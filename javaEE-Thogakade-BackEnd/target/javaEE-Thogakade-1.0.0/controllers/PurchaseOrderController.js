
import { validated1, validated2, validated3, validated4, validated5, validated6, validated7, validated8, validated9, validated10, validated11 } from "./validations/OrderValidation.js";


var Orders = [];

$(document).ready(function () {
    loadCustomerIds();
    loadItemIds();
});


function loadCustomerIds() {
    let selectId = $("#selectCustomerId");
    selectId.empty();

    $.ajax({
        method: "GET",
        url: "http://localhost:8080/app/customer",
        async: true,
        dataType: 'json',
        success: function (resp) {
            console.log(resp);
            for (let i = 0; i < resp.length; i++) {
                let option = `<option value="${resp[i].id}">${resp[i].id}</option>`;
                selectId.append(option);
            }
        }
    });
}

function loadItemIds() {
    let selectId = $("#oSelectItem");
    selectId.empty();

    $.ajax({
        method: "GET",
        url: "http://localhost:8080/app/item",
        async: true,
        dataType: 'json',
        success: function (resp) {
            console.log(resp);
            for (let i = 0; i < resp.length; i++) {
                let option = `<option value="${resp[i].code}">${resp[i].code}</option>`;
                selectId.append(option);
            }
        }
    });
}
    $("#orderNav").click(function () {
        const customerFormVar = document.querySelector("#customerForm");
        const itemFormVar = document.querySelector("#itemForm");
        const orderrFormVar = document.querySelector("#orderForm");
        const homeFormVar = document.querySelector("#homeeeeee");

        loadCustomerIds();
        loadItemIds();

        homeFormVar.style.display='none'
        customerFormVar.style.display = "none";
        itemFormVar.style.display = "none";
        orderrFormVar.style.display = "inline";

    });

$("#selectCustomerId").change(function () {
    const selectedValue = $(this).val();
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/app/customer",
        async: true,
        dataType: 'json',
        data: {
            customerId: selectedValue
        },
        success: function (resp) {
            if (Array.isArray(resp) && resp.length > 0) {
                const customer = resp[0];

                $("#oCName").val(customer.name);
                $("#CustomerIDORderForm").val(customer.id);
                $("#oCAddress").val(customer.address);
                $("#oCSalary").val(customer.salary);

                validated1();
                validated2();
                validated3();
                validated4();
                validated5();
                validated6();
                validated7();
                validated8();
                validated9();
                validated10();
                validated11();
            } else {
                console.log("No customer found with the specified ID");
            }
        }
    });
});

    $("#oSelectItem").change(function () {
        const selectedValue = $(this).val();

        $.ajax({
            method: "GET",
            url: "http://localhost:8080/app/item",
            async: true,
            dataType: 'json',
            data: {
                itemId: selectedValue
            },
            success: function (resp) {
                if (Array.isArray(resp) && resp.length > 0) {
                    const item = resp[0];
                    $("#itemID").val(item.code);
                    $("#ItemNameOrder").val(item.description);
                    $("#iOPrice").val(item.unitPrice);
                    $("#iOQty").val(item.qtyOnHand);

                    validated1();
                    validated2();
                    validated3();
                    validated4();
                    validated5();
                    validated6();
                    validated7();
                    validated8();
                    validated9();
                    validated10();
                    validated11();
                } else {
                    console.log("No item found with the specified ID");
                }
            }
        });



    });
    $("#addToItemBtn").click(function () {


        var price = parseInt($("#iOPrice").val());
        var qty = parseInt($("#oqty").val());
        var total = price * qty;
        var itemID = $("#itemID").val();
        var itemName = $("#ItemNameOrder").val();



        updateOrderTable();
        loadTotal();
    });
    function loadTotal() {
        var fullTotal=0;
        for (let i = 0; i < Orders.length; i++) {
            fullTotal+=parseInt(Orders[i].total);
        }
        $("#totalTxt").text(fullTotal);
        $("#OrderSubTotal").text(fullTotal);
    }

    $("#oSaveBtn").click(function () {
            $("#oId").val("");
           $("#date").val("");
             $("#CustomerIDORderForm").val("");
            $("#itemID").val("");
            $("#oCSalary").val("");
            $("#ItemNameOrder").val("");
            $("#iOPrice").val("");
            $("#oqty").val("");
            $("#iOQty").val("");
            $("#oCName").val("");
            $("#orderCashTxt").val("");
            $("#orderDiscountTxt").val("");
            $("#tblOrderBody").empty();
            $("#orderBalanceTxt").val("");
             $("#OrderSubTotal").text("");
             $("#totalTxt").text("");
            $("#selectCustomerId").children().remove();
            $("#oSelectItem").children().remove();


    });


    $("#orderCashTxt").keyup(function (e) {
        var total =parseInt($("#OrderSubTotal").text());
        var cash =parseInt($("#orderCashTxt").val());
        var balance =  cash-total;
        $("#orderBalanceTxt").val(balance);
    });


    $("#orderDiscountTxt").keyup(function (e) {
        var total =parseInt($("#totalTxt").text());
        var discount =parseInt($("#orderDiscountTxt").val());

        if ($("#orderDiscountTxt")=="" ){
            var discount =0;
        }
        if (discount===0){
            $("#OrderSubTotal").text(total);
        }else{
            var balance = total-(total*(discount/100));
            $("#OrderSubTotal").text(balance);
        }

    });

function updateOrderTable() {
    $("#tblOrderBody").empty();

    Orders.forEach((orders) => {
        $("#tblOrderBody").append(`<tr><td>${orders.itemID}</td><td>${orders.itemName}</td><td>${orders.unitPrice}</td><td>${orders.Qty}</td><td>${orders.total}</td></tr>`);
    });
}