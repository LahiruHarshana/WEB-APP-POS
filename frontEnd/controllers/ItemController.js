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
    updateItemTable();
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
        url: "http://localhost:8080/check/item",
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

    $.ajax({
        method: "GET",
        url: "http://localhost:8080/check/item",
        async: true,
        dataType: 'json',
        data: {
            itemId: searchValue
        },
        success: function (item) {
            if (item) {
                const items = item[0];
                $("#iID").val(items.code);
                $("#IIName").val(items.description);
                $("#i-Price").val(items.unitPrice);
                $("#Iqty").val(items.qtyOnHand);
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
        url: "http://localhost:8080/check/item",
        contentType: "application/json",
        async: true,
        data: JSON.stringify(item),
        success: function (data) {
            alert("Item has been saved successfully");
        },
        error: function (data) {
            console.error(data);
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
        url: "http://localhost:8080/check/item",
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
        url: `http://localhost:8080/check/item/${itemId}`,
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

