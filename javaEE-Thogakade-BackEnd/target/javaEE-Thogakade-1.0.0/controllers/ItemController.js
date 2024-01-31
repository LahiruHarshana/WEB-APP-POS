var customerFormVar = document.querySelector("#customerForm");
var itemFormVar = document.querySelector("#itemForm");
var orderrFormVar = document.querySelector("#orderForm");
var homeFormVar = document.querySelector("#homeeeeee");

homeFormVar.style.display = 'inline';
customerFormVar.style.display = 'none';
itemFormVar.style.display = 'none';
orderrFormVar.style.display = 'none';


$("#homeNav").click(function () {
    homeFormVar.style.display = 'inline';
    customerFormVar.style.display = 'none';
    itemFormVar.style.display = 'none';
    orderrFormVar.style.display = 'none';
});

$("#customerNav").click(function () {
    homeFormVar.style.display = 'none';
    customerFormVar.style.display = 'inline';
    itemFormVar.style.display = 'none';
    orderrFormVar.style.display = 'none';
});

$("#itemNav").click(function () {
    homeFormVar.style.display = 'none';
    customerFormVar.style.display = 'none';
    itemFormVar.style.display = 'inline';
    orderrFormVar.style.display = 'none';
});

$("#orderNav").click(function () {
    homeFormVar.style.display = 'none';
    customerFormVar.style.display = 'none';
    itemFormVar.style.display = 'none';
    orderrFormVar.style.display = 'inline';
});



var $tblItem = $("#itemTbl");
var $iIdTxt = $("#iID");
var $iNameTxt = $("#IIName");
var $iPrice = $("#i-Price");
var $iQty = $("#Iqty");

$("#iSaveBtn").click(() => {
    saveItem();
    updateItemTable();

});

$("#iUpdateBtn").click(() => {
    updateItem();
    updateItemTable();

});

$("#iDeleteBtn").click(() => {
    deleteItem();
    updateItemTable();

});


function updateItemTable() {
    $tblItem.empty();

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/app/item",
        success: function (items) {
            for (let i in items) {
                const id = items[i].code;
                const name = items[i].description;
                const price = items[i].unitPrice;
                const Qty = items[i].qtyOnHand;

                const row = `<tr><td>${id}</td><td>${name}</td><td>${price}</td><td>${Qty}</td></tr>`;
                $tblItem.append(row);
            }
        },
        error: function (xhr, status, error) {
            alert("Failed to load items: " + error);
        }
    });
}

function searchItem() {
    const searchValue = $("#iSearchTxt").val();

    if (searchValue.trim() === "") {
        alert("Please enter a valid Item ID to search.");
        return;
    }

    $.ajax({
        type: "GET",
        url: `http://localhost:8080/app/item/${searchValue}`,
        success: function (item) {
            if (item) {
                $iNameTxt.val(item.description);
                $iIdTxt.val(item.code);
                $iPrice.val(item.unitPrice);
                $iQty.val(item.qtyOnHand);
            } else {
                alert("Item not found.");
            }
        },
        error: function (resp) {
            alert("Failed to fetch item details");
        }
    });
}
$("#iSearchBtn").click(searchItem);


function saveItem() {
    var item = {
        code: $iIdTxt.val(),
        description: $iNameTxt.val(),
        unitPrice: $iPrice.val(),
        qtyOnHand: $iQty.val(),
    };

    $.ajax({
        method: "POST",
        url: "http://localhost:8080/app/item",
        contentType: "application/json",
        async: true,
        data: JSON.stringify(item),
        success: function (data) {
            alert("Item has been saved successfully");
        },
        error: function (data) {
            alert("Failed to save the item");
        },
    });
}

function updateItem() {
    var item = {
        code: $iIdTxt.val(),
        description: $iNameTxt.val(),
        unitPrice: $iPrice.val(),
        qtyOnHand: $iQty.val(),
    };

    $.ajax({
        method: "PUT",
        url: "http://localhost:8080/app/item",
        contentType: "application/json",
        async: true,
        data: JSON.stringify(item),
        success: function (data) {
            alert("Item has been updated successfully");
        },
        error: function (data) {
            alert("Failed to update the item");
        },
    });
}

function deleteItem() {
    var itemId = $iIdTxt.val();

    $.ajax({
        method: "DELETE",
        url: `http://localhost:8080/app/item/${itemId}`,
        contentType: "application/json",
        async: true,
        success: function (data) {
            alert("Item has been deleted successfully");
        },
        error: function (xhr, status, error) {
            alert("Failed to delete the item: " + error);
        },
    });
}

