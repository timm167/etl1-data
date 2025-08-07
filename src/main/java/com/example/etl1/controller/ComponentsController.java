package com.example.etl1.controller;

import com.example.etl1.model.*;
import com.example.etl1.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ComponentsController {

    @Autowired
    CaseRepository caseRepository;

    @Autowired
    CpuRepository cpuRepository;

    @Autowired
    CpuCoolerRepository cpuCoolerRepository;

    @Autowired
    FanRpmRepository fanRPMRepository;

    @Autowired
    FanNoiseLevelRepository fanNoiseLevelRepository;

    @Autowired
    GraphicsCardRepository graphicsCardRepository;

    @Autowired
    InternalStorageRepository internalStorageRepository;

    @Autowired
    MemoryRepository memoryRepository;

    @Autowired
    MemorySpeedRepository memorySpeedRepository;

    @Autowired
    MemoryModuleRepository memoryModuleRepository;

    @Autowired
    MotherboardRepository motherboardRepository;

    @Autowired
    PowerSupplyRepository powerSupplyRepository;

    @GetMapping("/load-component-data")
    public void populateComponentsData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Case[] cases = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/case.json"), Case[].class);
        Cpu[] cpus = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/cpu.json"), Cpu[].class);
        CpuCooler[] cpuCoolers = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/cpu-cooler.json"), CpuCooler[].class);
        GraphicsCard[] graphicsCards = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/graphics-card.json"), GraphicsCard[].class);
        InternalStorage[] internalStorages = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/internal-storage-device.json"), InternalStorage[].class);
        Memory[] memory = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/memory.json"), Memory[].class);
        Motherboard[] motherboards = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/motherboard.json"), Motherboard[].class);
        PowerSupply[] powerSupplies = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/power-supply.json"), PowerSupply[].class);

        caseRepository.saveAll(Arrays.asList(cases));
        cpuRepository.saveAll(Arrays.asList(cpus));

        cpuCoolerRepository.saveAll(Arrays.asList(cpuCoolers));

        List<FanRpm> allRPMs = new ArrayList<>();
        List<FanNoiseLevel> allNoiseLevels = new ArrayList<>();

        for (CpuCooler cpuCooler : cpuCoolers) {
            List<FanRpm> rpms = cpuCooler.getRpm();
            if (!rpms.isEmpty()) {
                for (FanRpm rpm : rpms) {
                    if (rpm != null) {
                        rpm.setCpu_cooler(cpuCooler);
                        allRPMs.add(rpm);
                    }
                }
            }
            List<FanNoiseLevel> noiseLevels = cpuCooler.getNoise_level();
            if (!noiseLevels.isEmpty()) {
                for (FanNoiseLevel noiseLevel : noiseLevels) {
                    if (noiseLevel != null) {
                        noiseLevel.setCpu_cooler(cpuCooler);
                        allNoiseLevels.add(noiseLevel);
                    }
                }
            }
        }

        fanRPMRepository.saveAll(allRPMs);
        fanNoiseLevelRepository.saveAll(allNoiseLevels);
        graphicsCardRepository.saveAll(Arrays.asList(graphicsCards));
        internalStorageRepository.saveAll(Arrays.asList(internalStorages));
        memoryRepository.saveAll(Arrays.asList(memory));

        List<MemorySpeed> allMemorySpeeds = new ArrayList<>();
        List<MemoryModule> allMemoryModules = new ArrayList<>();

        for (Memory mem : memory) {
            List<MemorySpeed> memorySpeeds = mem.getSpeed();
            if (!memorySpeeds.isEmpty()) {
                for (MemorySpeed memorySpeed : memorySpeeds) {
                    if (memorySpeed != null) {
                        memorySpeed.setMemory(mem);
                        allMemorySpeeds.add(memorySpeed);
                    }
                }
            }

            List<MemoryModule> memoryModules = mem.getModules();
            if (!memoryModules.isEmpty()) {
                for (MemoryModule memoryModule: memoryModules) {
                    if (memoryModule != null) {
                        memoryModule.setMemory(mem);
                        allMemoryModules.add(memoryModule);
                    }
                }
            }
        }

        memorySpeedRepository.saveAll(allMemorySpeeds);
        memoryModuleRepository.saveAll(allMemoryModules);

        motherboardRepository.saveAll(Arrays.asList(motherboards));
        powerSupplyRepository.saveAll(Arrays.asList(powerSupplies));
    }

    @GetMapping("/components/cases")
    public ModelAndView viewCases() {
        ModelAndView modelAndView = new ModelAndView("/components/cases");
        modelAndView.addObject("cases", caseRepository.findAll());
        modelAndView.addObject("sortBy", "");
        modelAndView.addObject("order", "");
        return modelAndView;
    }

    @GetMapping("/components/cpus")
    public ModelAndView viewCpus() {
        ModelAndView modelAndView = new ModelAndView("/components/cpus");
        modelAndView.addObject("cpus", cpuRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/components/cpu-coolers")
    public ModelAndView viewCpuCoolers() {
        ModelAndView modelAndView = new ModelAndView("/components/cpu-coolers");
        modelAndView.addObject("cpu_coolers", cpuCoolerRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/components/graphics-cards")
    public ModelAndView viewGraphicsCards() {
        ModelAndView modelAndView = new ModelAndView("/components/graphics-cards");
        modelAndView.addObject("graphics_cards", graphicsCardRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/components/internal-storage")
    public ModelAndView viewInternalStorage() {
        ModelAndView modelAndView = new ModelAndView("/components/internal-storage");
        modelAndView.addObject("internal_storages", internalStorageRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/components/memory")
    public ModelAndView viewMemory() {
        ModelAndView modelAndView = new ModelAndView("/components/memory");
        modelAndView.addObject("memory", memoryRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/components/motherboards")
    public ModelAndView viewMotherboards() {
        ModelAndView modelAndView = new ModelAndView("/components/motherboards");
        modelAndView.addObject("motherboards", motherboardRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/components/power-supplies")
    public ModelAndView viewPowerSupplies() {
        ModelAndView modelAndView = new ModelAndView("/components/power-supplies");
        modelAndView.addObject("power_supplies", powerSupplyRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/components/graphics-cards/nvidia")
    public ModelAndView viewNvidiaGraphicsCards() {
        ModelAndView modelAndView = new ModelAndView("/components/graphics-cards");
        modelAndView.addObject("graphics_cards", graphicsCardRepository.findByChipsetContaining("GeForce"));
        return modelAndView;
    }

    @GetMapping("/components/graphics-cards/amd")
    public ModelAndView viewAmdGraphicsCards() {
        ModelAndView modelAndView = new ModelAndView("/components/graphics-cards");
        modelAndView.addObject("graphics_cards", graphicsCardRepository.findByChipsetContaining("Radeon"));
        return modelAndView;
    }
    @GetMapping("/components/cpus/intel")
    public ModelAndView viewIntelCpus() {
        ModelAndView modelAndView = new ModelAndView("/components/cpus");
        modelAndView.addObject("cpus", cpuRepository.findByNameContaining("Intel"));
        return modelAndView;
    }

    @GetMapping("/components/cpus/amd")
    public ModelAndView viewAmdCpus() {
        ModelAndView modelAndView = new ModelAndView("/components/cpus");
        modelAndView.addObject("cpus", cpuRepository.findByNameContaining("AMD"));
        return modelAndView;
    }

    @GetMapping("/components/cases/sort")
    public ModelAndView viewSortedCases(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/components/cases");

        Sort.Direction direction = null;

        if (order.equals("Descending")) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        String property = switch (sortBy) {
            case "Name" -> "name";
            case "Price" -> "price";
            case "Size" -> "externalVolume";
            default -> null;
        };

        List<Case> cases = caseRepository.findAll(Sort.by(direction, property));
        modelAndView.addObject("cases", cases);
        return modelAndView;
    }
}
