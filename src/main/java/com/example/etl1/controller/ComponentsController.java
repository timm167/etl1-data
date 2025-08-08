package com.example.etl1.controller;

import com.example.etl1.model.*;
import com.example.etl1.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
        caseRepository.deleteAll();
        cpuRepository.deleteAll();
        cpuCoolerRepository.deleteAll();
        graphicsCardRepository.deleteAll();
        internalStorageRepository.deleteAll();
        memoryRepository.deleteAll();
        motherboardRepository.deleteAll();
        powerSupplyRepository.deleteAll();

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
                for (MemoryModule memoryModule : memoryModules) {
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

        return "redirect:/index";
    }

//    @GetMapping("/components/cases")
//    public ModelAndView viewCases() {
//        ModelAndView modelAndView = new ModelAndView("/components/cases");
//        modelAndView.addObject("cases", caseRepository.findAll());
//        modelAndView.addObject("sortBy", "");
//        modelAndView.addObject("order", "");
//        return modelAndView;
//    }

//    @GetMapping("/components/cpus")
//    public ModelAndView viewCpus() {
//        ModelAndView modelAndView = new ModelAndView("/components/cpus");
//        modelAndView.addObject("cpus", cpuRepository.findAll());
//        modelAndView.addObject("sortBy", "");
//        modelAndView.addObject("order", "");
//        return modelAndView;
//    }

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

    @GetMapping("/components/cases")
    public ModelAndView viewSortedCases(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/components/cases");

        Sort.Direction direction;

        if (order != null && order.equals("Descending")) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        List<Case> cases;

        if (sortBy != null) {
            String property = switch (sortBy) {
                case "Name" -> "name";
                case "Price" -> "price";
                case "Size" -> "externalVolume";
                default -> null;
            };

            if (property != null) {
                cases = caseRepository.findAll(Sort.by(direction, property));
            } else {
                cases = (List<Case>) caseRepository.findAll();
            }
        } else {
            cases = (List<Case>) caseRepository.findAll();
        }

        modelAndView.addObject("cases", cases);
        return modelAndView;
    }

    @GetMapping("/components/cpus")
    public ModelAndView viewSortedCpus(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/components/cpus");

        Sort.Direction direction;

        if (order != null && order.equals("Descending")) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        List<Cpu> cpus;

        if (sortBy != null) {
            String property = switch (sortBy) {
                case "Name" -> "name";
                case "Price" -> "price";
                case "Core Clock" -> "coreClock";
                default -> null;
            };

            if (property != null) {
                cpus = cpuRepository.findAll(Sort.by(direction, property));
            } else {
                cpus = (List<Cpu>) cpuRepository.findAll();
            }
        } else {
            cpus = (List<Cpu>) cpuRepository.findAll();
        }

        modelAndView.addObject("cpus", cpus);
        return modelAndView;
    }

    @GetMapping("/components/cpu-coolers/sort")
    public ModelAndView viewSortedCpuCoolers(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/components/cpu-coolers");

        Sort.Direction direction = null;

        if (order.equals("Descending")) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        String property = switch (sortBy) {
            case "Name" -> "name";
            case "Price" -> "price";
            default -> null;
        };

        List<CpuCooler> cpuCoolers = cpuCoolerRepository.findAll(Sort.by(direction, property));
        modelAndView.addObject("cpu_coolers", cpuCoolers);
        return modelAndView;
    }

    @GetMapping("/components/graphics-cards/sort")
    public ModelAndView viewSortedGraphicsCards(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/components/graphics-cards");

        Sort.Direction direction = null;

        if (order.equals("Descending")) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        String property = switch (sortBy) {
            case "Name" -> "name";
            case "Price" -> "price";
            case "Core Clock" -> "coreClock";
            default -> null;
        };

        List<GraphicsCard> graphicsCards = graphicsCardRepository.findAll(Sort.by(direction, property));
        modelAndView.addObject("graphics_cards", graphicsCards);
        return modelAndView;
    }

    @GetMapping("/components/internal-storage/sort")
    public ModelAndView viewSortedInternalStorage(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/components/internal-storage");

        Sort.Direction direction = null;

        if (order.equals("Descending")) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        String property = switch (sortBy) {
            case "Name" -> "name";
            case "Price" -> "price";
            case "Capacity" -> "capacity";
            default -> null;
        };

        List<InternalStorage> internalStorages = internalStorageRepository.findAll(Sort.by(direction, property));
        modelAndView.addObject("internal_storages", internalStorages);
        return modelAndView;
    }

    @GetMapping("/components/memory/sort")
    public ModelAndView viewSortedMemory(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/components/memory");

        Sort.Direction direction = null;

        if (order.equals("Descending")) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        String property = switch (sortBy) {
            case "Name" -> "name";
            case "Price" -> "price";
            case "Speed" -> "speed.speed";
            default -> null;
        };

        List<Memory> memory = memoryRepository.findAll(Sort.by(direction, property));
        modelAndView.addObject("memory", memory);
        return modelAndView;
    }

    @GetMapping("/components/motherboards/sort")
    public ModelAndView viewSortedMotherboards(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/components/motherboards");

        Sort.Direction direction = null;

        if (order.equals("Descending")) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        String property = switch (sortBy) {
            case "Name" -> "name";
            case "Price" -> "price";
            default -> null;
        };

        List<Motherboard> motherboards = motherboardRepository.findAll(Sort.by(direction, property));
        modelAndView.addObject("motherboards", motherboards);
        return modelAndView;
    }

    @GetMapping("/components/power-supplies/sort")
    public ModelAndView viewSortedPowerSupplies(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/components/power-supplies");

        Sort.Direction direction = null;

        if (order.equals("Descending")) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        String property = switch (sortBy) {
            case "Name" -> "name";
            case "Price" -> "price";
            case "Wattage" -> "wattage";
            default -> null;
        };

        List<PowerSupply> powerSupplies = powerSupplyRepository.findAll(Sort.by(direction, property));
        modelAndView.addObject("power_supplies", powerSupplies);
        return modelAndView;
    }
}
