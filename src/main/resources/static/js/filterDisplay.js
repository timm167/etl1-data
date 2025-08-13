window.onload = function() {
    document.getElementById("filterBy").dispatchEvent(new Event("change"));
}

function checkFilterBy(filterBySelect) {
    filterByOption = filterBySelect.value;

    switch (filterByOption) {
        case "Price":
            document.getElementById("filterOp").style.display = "";
            document.getElementById("numberFilter").style.display = "";
            document.getElementById("numberFilter").required = true;
            document.getElementById("priceFilterLabel").style.display = "";
            document.getElementById("cpuClockFilterLabel").style.display = "none";
            document.getElementById("gpuClockFilterLabel").style.display = "none";
            document.getElementById("caseSizeFilterLabel").style.display = "none";
            break;
        case "CPU clock speed":
            document.getElementById("filterOp").style.display = "";
            document.getElementById("numberFilter").style.display = "";
            document.getElementById("numberFilter").required = true;
            document.getElementById("priceFilterLabel").style.display = "none";
            document.getElementById("cpuClockFilterLabel").style.display = "";
            document.getElementById("gpuClockFilterLabel").style.display = "none";
            document.getElementById("caseSizeFilterLabel").style.display = "none";
            break;
        case "GPU clock speed":
            document.getElementById("filterOp").style.display = "";
            document.getElementById("numberFilter").style.display = "";
            document.getElementById("numberFilter").required = true;
            document.getElementById("priceFilterLabel").style.display = "none";
            document.getElementById("cpuClockFilterLabel").style.display = "none";
            document.getElementById("gpuClockFilterLabel").style.display = "";
            document.getElementById("caseSizeFilterLabel").style.display = "none";
            break;
        case "Volume":
            document.getElementById("filterOp").style.display = "";
            document.getElementById("numberFilter").style.display = "";
            document.getElementById("numberFilter").required = true;
            document.getElementById("priceFilterLabel").style.display = "none";
            document.getElementById("cpuClockFilterLabel").style.display = "none";
            document.getElementById("gpuClockFilterLabel").style.display = "none";
            document.getElementById("caseSizeFilterLabel").style.display = "";
            break;
        default:
            document.getElementById("filterOp").style.display = "none";
            document.getElementById("numberFilter").style.display = "none";
            document.getElementById("numberFilter").required = false;
            document.getElementById("priceFilterLabel").style.display = "none";
            document.getElementById("cpuClockFilterLabel").style.display = "none";
            document.getElementById("gpuClockFilterLabel").style.display = "none";
            document.getElementById("caseSizeFilterLabel").style.display = "none";
            break;
    }
}