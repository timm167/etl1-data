window.onload = function() {
    document.getElementById("filterBy").dispatchEvent(new Event("change"));
}

function checkFilterBy(filterBySelect) {
    const filterOp = document.getElementById("filterOp");
    const numberFilterOptions = document.querySelectorAll(".numberFilterOption");
    const brandFilterOptions = document.querySelectorAll(".brandFilterOption");
    const currencyLabel = document.getElementById("priceFilterLabel")
    const numberFilter = document.getElementById("numberFilter");
    const unitLabel = document.getElementById("filterUnitLabel");

    filterOp.style.display = "";
    filterOp.value = "at least";

    numberFilterOptions.forEach(function(option) {
        option.style.display = "";
    });

    brandFilterOptions.forEach(function(option) {
        option.style.display = "none";
    });

//    document.querySelectorAll(".filterLabel").forEach(function(label) {
//        label.style.display = "none";
//    });
    currencyLabel.style.display = "none";
    numberFilter.style.display = "";
    numberFilter.required = true;
    unitLabel.style.display = "";

    switch (filterBySelect.value) {
        case "Price":
            currencyLabel.style.display = "";
            unitLabel.style.display = "none";
            break;
        case "CPU clock speed":
            unitLabel.innerHTML = "GHz";
            break;
        case "GPU clock speed":
            unitLabel.innerHTML = "MHz";
            break;
        case "Volume":
            unitLabel.innerHTML = "L";
            break;
        case "Wattage":
            unitLabel.innerHTML = "W";
            break;
        case "Capacity":
            unitLabel.innerHTML = "GB";
            break;
        case "Memory speed":
            unitLabel.innerHTML = "MHz";
            break;
        case "CPU brand":
        case "GPU brand":
            filterOp.value = (filterBySelect.value === "CPU brand") ? "Intel" : "NVIDIA"

            brandFilterOptions.forEach(function(option) {
                option.style.display = "";
            });

            numberFilterOptions.forEach(function(option) {
                option.style.display = "none";
            });

            numberFilter.style.display = "none";
            numberFilter.required = false;
            unitLabel.style.display = "none";
            break;
        default:
            filterOp.style.display = "none";
            numberFilter.style.display = "none";
            numberFilter.required = false;
            unitLabel.style.display = "none";
            break;
    }
}