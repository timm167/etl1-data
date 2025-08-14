window.onload = function() {
    document.getElementById("filterBy").dispatchEvent(new Event("change"));
}

function checkFilterBy(filterBySelect) {
    const filterOp = document.getElementById("filterOp");
    const numberFilter = document.getElementById("numberFilter");

    filterOp.style.display = "";
    numberFilter.style.display = "";
    numberFilter.required = true;

    document.querySelectorAll(".numberFilterOption").forEach(function(option) {
        option.style.display = "";
    });

    document.querySelectorAll(".brandFilterOption").forEach(function(option) {
        option.style.display = "none";
    });

    document.querySelectorAll(".filterLabel").forEach(function(label) {
        label.style.display = "none";
    });

    switch (filterBySelect.value) {
        case "Price":
            document.getElementById("priceFilterLabel").style.display = "";
            break;
        case "CPU clock speed":
            document.getElementById("cpuClockFilterLabel").style.display = "";
            break;
        case "GPU clock speed":
            document.getElementById("gpuClockFilterLabel").style.display = "";
            break;
        case "Volume":
            document.getElementById("caseSizeFilterLabel").style.display = "";
            break;
        case "Wattage":
            document.getElementById("psuWattageFilterLabel").style.display = "";
            break;
        case "Capacity":
            document.getElementById("storageCapacityFilterLabel").style.display = "";
            break;
        case "Memory speed":
            document.getElementById("memorySpeedFilterLabel").style.display = "";
            break;
        case "CPU brand":
        case "GPU brand":
            filterOp.value = (filterBySelect.value === "CPU brand") ? "Intel" : "NVIDIA"

            document.querySelectorAll(".brandFilterOption").forEach(function(option) {
                option.style.display = "";
            });

            document.querySelectorAll(".numberFilterOption").forEach(function(option) {
                option.style.display = "none";
            });

            numberFilter.style.display = "none";
            numberFilter.required = false;
            break;
        default:
            filterOp.style.display = "none";
            numberFilter.style.display = "none";
            numberFilter.required = false;
            break;
    }
}