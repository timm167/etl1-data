package com.example.etl1.controller;

import com.example.etl1.model.components.*;
import com.example.etl1.repository.components.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
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
    public String populateComponentsData() throws IOException {
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

        convertComponentPriceToPounds(cases);
        convertComponentPriceToPounds(cpus);
        convertComponentPriceToPounds(cpuCoolers);
        convertComponentPriceToPounds(graphicsCards);
        convertComponentPriceToPounds(internalStorages);
        convertComponentPriceToPounds(memory);
        convertComponentPriceToPounds(motherboards);
        convertComponentPriceToPounds(powerSupplies);

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

        return "redirect:/";
    }

    @GetMapping("/components/cases")
    public ModelAndView viewCases(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/components/cases");
        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            modelAndView.addObject("cases", caseRepository.findAll(sort));
        } else {
            modelAndView.addObject("cases", caseRepository.findAll());
        }

        return modelAndView;
    }

    @GetMapping("/components/cpus")
    public ModelAndView viewCpus(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/components/cpus");
        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            modelAndView.addObject("cpus", cpuRepository.findAll(sort));
        } else {
            modelAndView.addObject("cpus", cpuRepository.findAll());
        }

        return modelAndView;
    }

    @GetMapping("/components/cpu-coolers")
    public ModelAndView viewCpuCoolers(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/components/cpu-coolers");
        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            modelAndView.addObject("cpu_coolers", cpuCoolerRepository.findAll(sort));
        } else {
            modelAndView.addObject("cpu_coolers", cpuCoolerRepository.findAll());
        }

        return modelAndView;
    }

    @GetMapping("/components/graphics-cards")
    public ModelAndView viewGraphicsCards(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/components/graphics-cards");
        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            modelAndView.addObject("graphics_cards", graphicsCardRepository.findAll(sort));
        } else {
            modelAndView.addObject("graphics_cards", graphicsCardRepository.findAll());
        }

        return modelAndView;
    }

    @GetMapping("/components/internal-storage")
    public ModelAndView viewInternalStorage(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/components/internal-storage");
        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            modelAndView.addObject("internal_storages", internalStorageRepository.findAll(sort));
        } else {
            modelAndView.addObject("internal_storages", internalStorageRepository.findAll());
        }

        return modelAndView;
    }

    @GetMapping("/components/memory")
    public ModelAndView viewMemory(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/components/memory");
        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            modelAndView.addObject("memory", memoryRepository.findAll(sort));
        } else {
            modelAndView.addObject("memory", memoryRepository.findAll());
        }

        return modelAndView;
    }

    @GetMapping("/components/motherboards")
    public ModelAndView viewMotherboards(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/components/motherboards");
        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            modelAndView.addObject("motherboards", motherboardRepository.findAll(sort));
        } else {
            modelAndView.addObject("motherboards", motherboardRepository.findAll());
        }

        return modelAndView;
    }

    @GetMapping("/components/power-supplies")
    public ModelAndView viewPowerSupplies(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/components/power-supplies");
        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            modelAndView.addObject("power_supplies", powerSupplyRepository.findAll(sort));
        } else {
            modelAndView.addObject("power_supplies", powerSupplyRepository.findAll());
        }

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

//    public static Sort getSortMethod(String sortBy, String order) {
//        Sort.Direction direction;
//
//        if (sortBy != null) {
//            if (order != null && order.equals("Descending")) {
//                direction = Sort.Direction.DESC;
//            } else {
//                direction = Sort.Direction.ASC;
//            }
//
//            String property = switch (sortBy) {
//                case "Name" -> "name";
//                case "Price" -> "price";
//                case "Size" -> "externalVolume";
//                case "Core Clock" -> "coreClock";
//                case "Capacity" -> "capacity";
//                case "Speed" -> "speed.speed";
//                case "Wattage" -> "wattage";
//                default -> null;
//            };
//
//            if (property != null) {
//                return Sort.by(direction, property);
//            }
//        }
//
//        return null;
//    }

    private void convertComponentPriceToPounds(Component[] components) {
        for (Component component : components) {
            component.setPrice(component.getPrice() * 0.75);
        }
    }
}
