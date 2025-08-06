package com.example.etl1.Controllers;

import com.example.etl1.Models.*;
import com.example.etl1.Repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    CPURepository cpuRepository;

    @Autowired
    CPUCoolerRepository cpuCoolerRepository;

    @Autowired
    FanRPMRepository fanRPMRepository;

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
        CPU[] cpus = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/cpu.json"), CPU[].class);
        CPUCooler[] cpuCoolers = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/cpu-cooler.json"), CPUCooler[].class);
        GraphicsCard[] graphicsCards = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/graphics-card.json"), GraphicsCard[].class);
        InternalStorage[] internalStorages = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/internal-storage-device.json"), InternalStorage[].class);
        Memory[] memory = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/memory.json"), Memory[].class);
        Motherboard[] motherboards = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/motherboard.json"), Motherboard[].class);
        PowerSupply[] powerSupplies = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/power-supply.json"), PowerSupply[].class);

        caseRepository.saveAll(Arrays.asList(cases));
        cpuRepository.saveAll(Arrays.asList(cpus));

        cpuCoolerRepository.saveAll(Arrays.asList(cpuCoolers));

        List<FanRPM> allRPMs = new ArrayList<>();
        List<FanNoiseLevel> allNoiseLevels = new ArrayList<>();

        for (CPUCooler cpuCooler : cpuCoolers) {
            List<FanRPM> rpms = cpuCooler.getRpm();
            if (!rpms.isEmpty()) {
                for (FanRPM rpm : rpms) {
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
}
