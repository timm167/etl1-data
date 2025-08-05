package com.example.etl1.Controllers;

import com.example.etl1.Records.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@RestController
public class ComponentsController {
    @GetMapping("/parts")
    public ModelAndView populateComponentsData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Case[] cases = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/case.json"), Case[].class);
        CPU[] cpus = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/cpu.json"), CPU[].class);
        CPUCooler[] cpuCoolers = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/cpu-cooler.json"), CPUCooler[].class);
        GraphicsCard[] graphicsCards = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/graphics-card.json"), GraphicsCard[].class);
        InternalStorageDevice[] internalStorageDevices = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/internal-storage-device.json"), InternalStorageDevice[].class);
        Memory[] memory = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/memory.json"), Memory[].class);
        Motherboard[] motherboard = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/motherboard.json"), Motherboard[].class);
        PowerSupply[] powerSupplies = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/PC_Components_Data/power-supply.json"), PowerSupply[].class);
        ModelAndView modelAndView = new ModelAndView("/parts");

        modelAndView.addObject("cpuName", cpus[0].name());

        return modelAndView;
    }
}
