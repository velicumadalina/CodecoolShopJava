let price = document.getElementById("discount");

function changePrice(){
    let priceValue = parseInt(price.innerText.split(" ")[0]);
    let discountedValue = priceValue - ((priceValue*15)/100);
    price.innerText = discountedValue + "USD";
}

