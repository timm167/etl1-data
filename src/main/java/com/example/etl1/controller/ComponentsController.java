package com.example.etl1.controller;

import com.example.etl1.repository.components.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ComponentsController {

    @Autowired
    CaseRepository caseRepository;

    @Autowired
    CpuRepository cpuRepository;

    @Autowired
    CpuCoolerRepository cpuCoolerRepository;

    @Autowired
    GraphicsCardRepository graphicsCardRepository;

    @Autowired
    InternalStorageRepository internalStorageRepository;

    @Autowired
    MemoryRepository memoryRepository;

    @Autowired
    MotherboardRepository motherboardRepository;

    @Autowired
    PowerSupplyRepository powerSupplyRepository;

    @GetMapping("/components/cases")
    public ModelAndView viewCases(String sortBy, String order, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/components/cases");
        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            modelAndView.addObject("cases", caseRepository.findAll(sort));
        } else {
            modelAndView.addObject("cases", caseRepository.findAll());
        }

        modelAndView.addObject("componentIds", session.getAttribute("componentIds"));

        return modelAndView;
    }

    @GetMapping("/components/cpus")
    public ModelAndView viewCpus(String sortBy, String order, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/components/cpus");
        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            modelAndView.addObject("cpus", cpuRepository.findAll(sort));
        } else {
            modelAndView.addObject("cpus", cpuRepository.findAll());
        }

        modelAndView.addObject("componentIds", session.getAttribute("componentIds"));

        return modelAndView;
    }

    @GetMapping("/components/cpu-coolers")
    public ModelAndView viewCpuCoolers(String sortBy, String order, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/components/cpu-coolers");
        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            modelAndView.addObject("cpu_coolers", cpuCoolerRepository.findAll(sort));
        } else {
            modelAndView.addObject("cpu_coolers", cpuCoolerRepository.findAll());
        }

        modelAndView.addObject("componentIds", session.getAttribute("componentIds"));

        return modelAndView;
    }

    @GetMapping("/components/graphics-cards")
    public ModelAndView viewGraphicsCards(String sortBy, String order, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/components/graphics-cards");
        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            modelAndView.addObject("graphics_cards", graphicsCardRepository.findAll(sort));
        } else {
            modelAndView.addObject("graphics_cards", graphicsCardRepository.findAll());
        }

        modelAndView.addObject("componentIds", session.getAttribute("componentIds"));

        return modelAndView;
    }

    @GetMapping("/components/internal-storage")
    public ModelAndView viewInternalStorage(String sortBy, String order, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/components/internal-storage");
        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            modelAndView.addObject("internal_storages", internalStorageRepository.findAll(sort));
        } else {
            modelAndView.addObject("internal_storages", internalStorageRepository.findAll());
        }

        modelAndView.addObject("componentIds", session.getAttribute("componentIds"));

        return modelAndView;
    }

    @GetMapping("/components/memory")
    public ModelAndView viewMemory(String sortBy, String order, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/components/memory");
        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            modelAndView.addObject("memory", memoryRepository.findAll(sort));
        } else {
            modelAndView.addObject("memory", memoryRepository.findAll());
        }

        modelAndView.addObject("componentIds", session.getAttribute("componentIds"));

        return modelAndView;
    }

    @GetMapping("/components/motherboards")
    public ModelAndView viewMotherboards(String sortBy, String order, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/components/motherboards");
        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            modelAndView.addObject("motherboards", motherboardRepository.findAll(sort));
        } else {
            modelAndView.addObject("motherboards", motherboardRepository.findAll());
        }

        modelAndView.addObject("componentIds", session.getAttribute("componentIds"));

        return modelAndView;
    }

    @GetMapping("/components/power-supplies")
    public ModelAndView viewPowerSupplies(String sortBy, String order, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/components/power-supplies");
        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            modelAndView.addObject("power_supplies", powerSupplyRepository.findAll(sort));
        } else {
            modelAndView.addObject("power_supplies", powerSupplyRepository.findAll());
        }

        modelAndView.addObject("componentIds", session.getAttribute("componentIds"));

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
}
