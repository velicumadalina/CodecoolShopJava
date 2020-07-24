function showPayment() {
    let paypal = document.getElementById("paypal");
    let card = document.getElementById("credit_card");
    // Get the output text
    var text = document.getElementById("text");

    // If the checkbox is checked, display the output text
    if (checkBox.checked === true){
        text.style.display = "block";
    } else {
        text.style.display = "none";
    }
}