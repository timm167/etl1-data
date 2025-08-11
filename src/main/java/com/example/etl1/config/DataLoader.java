package com.example.etl1.config;

import com.example.etl1.model.Product;
import com.example.etl1.model.components.*;
import com.example.etl1.repository.ProductRepository;
import com.example.etl1.repository.components.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class DataLoader implements CommandLineRunner {
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

    @Autowired
    ColorRepository colorRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public void run(String... args) throws IOException {
        loadComponentData();
        loadProductData();
    }

    private void loadComponentData() throws IOException {
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
    }

    private void loadProductData() {
        int count = 10;

        for (int i = 1; i <= count; i++) {
            var color = firstOrNull(colorRepository.findAll());
            var casePart = firstOrNull(caseRepository.findAll());
            var cpu = firstOrNull(cpuRepository.findAll());
            var cooler = firstOrNull(cpuCoolerRepository.findAll());
            var gpu = firstOrNull(graphicsCardRepository.findAll());
            var storage = firstOrNull(internalStorageRepository.findAll());
            var memory = firstOrNull(memoryRepository.findAll());
            var mobo = firstOrNull(motherboardRepository.findAll());
            var psu = firstOrNull(powerSupplyRepository.findAll());

            BigDecimal cost = BigDecimal.ZERO;
            if (casePart != null) cost = cost.add(BigDecimal.valueOf(casePart.getPrice()));
            if (cpu != null) cost = cost.add(BigDecimal.valueOf(cpu.getPrice()));
            if (cooler != null) cost = cost.add(BigDecimal.valueOf(cooler.getPrice()));
            if (gpu != null) cost = cost.add(BigDecimal.valueOf(gpu.getPrice()));
            if (storage != null) cost = cost.add(BigDecimal.valueOf(storage.getPrice()));
            if (memory != null) cost = cost.add(BigDecimal.valueOf(memory.getPrice()));
            if (mobo != null) cost = cost.add(BigDecimal.valueOf(mobo.getPrice()));
            if (psu != null) cost = cost.add(BigDecimal.valueOf(psu.getPrice()));

            BigDecimal price = cost.multiply(BigDecimal.valueOf(1.5));

            List<String> productNames = Arrays.asList(
                    "Carboniser 2000 CPU",
                    "TurboMax Desktop",
                    "NeonWave CPU",
                    "PixelPro CPU",
                    "VoltEdge Desktop",
                    "CosmoTech CPU",
                    "ByteStream Tower",
                    "QuantumFlex CPU",
                    "HyperCore Desktop",
                    "MegaSync CPU"
            );

            Product product = new Product();
            product.setName(productNames.get(i - 1));
            product.setCost(cost);
            product.setPrice(price);
            product.setColor(color);
            product.setCaseEntity(casePart);
            product.setCpu(cpu);
            product.setCpuCooler(cooler);
            product.setGraphicsCard(gpu);
            product.setInternalStorage(storage);
            product.setMemory(memory);
            product.setMotherboard(mobo);
            product.setPowerSupply(psu);

            productRepository.save(product);
        }
    }

    private void convertComponentPriceToPounds(com.example.etl1.model.components.Component[] components) {
        for (com.example.etl1.model.components.Component component : components) {
            component.setPrice(Math.round(component.getPrice() * 75) / 100.0);
        }
    }

    private <T> T firstOrNull(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .findFirst()
                .orElse(null);
    }
}
