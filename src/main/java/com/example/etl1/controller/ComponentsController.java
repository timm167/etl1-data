package com.example.etl1.controller;

import com.example.etl1.model.ComponentIdCarrier;
import com.example.etl1.model.Product;
import com.example.etl1.model.components.*;
import com.example.etl1.repository.components.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    @GetMapping("/components/{type}")
    public ModelAndView viewComponents(@PathVariable(value="type") String type, String sortBy, String order, HttpSession session, String filterBy, String filterOp, Double numberFilter, String brand) {
        ModelAndView modelAndView = new ModelAndView("/components/" + type);

        ComponentRepository<? extends Component> componentRepository = switch (type) {
            case "cases" -> caseRepository;
            case "cpus" -> cpuRepository;
            case "cpu-coolers" -> cpuCoolerRepository;
            case "graphics-cards" -> graphicsCardRepository;
            case "internal-storage" -> internalStorageRepository;
            case "memory" -> memoryRepository;
            case "motherboards" -> motherboardRepository;
            case "power-supplies" -> powerSupplyRepository;
            default -> null;
        };

        if (componentRepository != null) {
            return getModelAndViewForComponent(modelAndView, componentRepository, type.replace("-", "_"), sortBy, order, session, filterBy, filterOp, numberFilter, brand);
        } else {
            return new ModelAndView(new RedirectView("/"));
        }
    }

    @PostMapping("/components/add-to-product")
    public String addComponentToCustomProduct(HttpSession session, ComponentIdCarrier componentIds) {
        Product customPc = (Product) session.getAttribute("customPc");

        if (customPc != null) {
            if (componentIds.getCaseId() != null) {
                caseRepository.findById(componentIds.getCaseId()).ifPresent(customPc::setCaseEntity);
            }

            if (componentIds.getCpuId() != null) {
                cpuRepository.findById(componentIds.getCpuId()).ifPresent(customPc::setCpu);
            }

            if (componentIds.getCpuCoolerId() != null) {
                cpuCoolerRepository.findById(componentIds.getCpuCoolerId()).ifPresent(customPc::setCpuCooler);
            }

            if (componentIds.getGraphicsCardId() != null) {
                graphicsCardRepository.findById(componentIds.getGraphicsCardId()).ifPresent(customPc::setGraphicsCard);
            }

            if (componentIds.getInternalStorageId() != null) {
                internalStorageRepository.findById(componentIds.getInternalStorageId()).ifPresent(customPc::setInternalStorage);
            }
            if (componentIds.getMemoryId() != null) {
                memoryRepository.findById(componentIds.getMemoryId()).ifPresent(customPc::setMemory);
            }
            if (componentIds.getMotherboardId() != null) {
                motherboardRepository.findById(componentIds.getMotherboardId()).ifPresent(customPc::setMotherboard);
            }
            if (componentIds.getPowerSupplyId() != null) {
                powerSupplyRepository.findById(componentIds.getPowerSupplyId()).ifPresent(customPc::setPowerSupply);
            }

            session.setAttribute("customPc", customPc);
        }

        return "redirect:/products/create";
    }

    private ModelAndView getModelAndViewForComponent(ModelAndView modelAndView, ComponentRepository<? extends Component> componentRepository, String componentAttributeName, String sortBy, String order, HttpSession session, String filterBy, String filterOp, Double numberFilter, String brand) {
        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            List<? extends Component> sortedComponents = componentRepository.findAll(sort);
            modelAndView.addObject(componentAttributeName, getFilteredComponents(sortedComponents, filterBy, filterOp, numberFilter, brand));
        } else {
            modelAndView.addObject(componentAttributeName, componentRepository.findAll());
        }

        modelAndView.addObject("componentIds", session.getAttribute("componentIds"));

        return modelAndView;
    }

    private List<? extends Component> getFilteredComponents(List<? extends Component> components, String filterBy, String filterOp, Double numberFilter, String brand) {
        Predicate<Double> compareForFilter;

        if (filterOp != null && filterOp.equals("at least")) {
            compareForFilter = value -> value >= numberFilter;
        } else {
            compareForFilter = value -> value <= numberFilter;
        }

        Predicate<Component> filterFunction;

        switch (filterBy) {
            case "Price":
                filterFunction = component -> compareForFilter.test(component.getPrice());
                break;
            case "CPU clock speed":
                filterFunction = component -> compareForFilter.test(((Cpu) component).getCoreClock());
                break;
            case "GPU clock speed":
                filterFunction = component -> compareForFilter.test((double) ((GraphicsCard) component).getCoreClock());
                break;
            case "Volume":
                filterFunction = component -> compareForFilter.test(((Case) component).getExternalVolume());
                break;
            case "Wattage":
                filterFunction = component -> compareForFilter.test((double) ((PowerSupply) component).getWattage());
                break;
            case "Memory speed":
                filterFunction = component -> compareForFilter.test(Double.valueOf(((Memory) component).getSpeed().getLast().getSpeed()));
                break;
            case "CPU brand":
                return cpuRepository.findByNameContaining(brand != null ? brand : filterOp);
            case "GPU brand":
                if (brand != null) {
                    return graphicsCardRepository.findByChipsetContaining(brand.equals("NVIDIA") ? "GeForce" : "Radeon");
                } else {
                    return graphicsCardRepository.findByChipsetContaining(filterOp.equals("NVIDIA") ? "GeForce" : "Radeon");
                }
            default:
                return components;
        }

        return components.stream().filter(filterFunction).collect(Collectors.toCollection(ArrayList::new));
    }
}
