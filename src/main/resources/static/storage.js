document.getElementById("rememberMe").addEventListener("click", function() {
    if (this.checked) {
        localStorage.setItem("rememberMe", true);
    } else {
        localStorage.removeItem("rememberMe");
    }
}, false);

