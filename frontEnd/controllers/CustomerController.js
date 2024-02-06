
var customerFormVar =document.querySelector("#customerForm");
var itemFormVar =document.querySelector("#itemForm");
var orderrFormVar =document.querySelector("#orderForm");
var homeFormVar = document.querySelector("#homeeeeee");

homeFormVar.style.display='inline'
customerFormVar.style.display='none';
itemFormVar.style.display='none';
orderrFormVar.style.display='none';

$("#homeNav").click(function (){
    homeFormVar.style.display='inline'
    customerFormVar.style.display='none';
    itemFormVar.style.display='none';
    orderrFormVar.style.display='none';
});

$("#customerNav").click(function (){
    homeFormVar.style.display='none'
    customerFormVar.style.display='inline';
    itemFormVar.style.display='none';
    orderrFormVar.style.display='none';
    updateCustomerTable();
});


$("#itemNav").click(function (){
    homeFormVar.style.display='none'
    customerFormVar.style.display='none';
    itemFormVar.style.display='inline';
    orderrFormVar.style.display='none';
});


$("#orderNav").click(function (){
    homeFormVar.style.display='none'
    customerFormVar.style.display='none';
    itemFormVar.style.display='none';
    orderrFormVar.style.display='inline';
});



var $tblCustomer = $("#tblCustomer");
var $cIdTxt = $("#cIdTxt");
var $cNameTxt = $("#cNameTxt");
var $cAddressTxt = $("#cAddressTxt");
var $cSalaryText = $("#cSalaryText");


$("#cSavebtn").click(() => {
    saveCustomer();
    updateCustomerTable();
});

$("#cUpdateBtn").click(() => {
    updateCustomer();
    updateCustomerTable();

});

function updateCustomerTable(){
    $("#tblCustomer").empty();

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/check/customer",
        success: function (customers) {
            for(let i in customers){
                let id = customers[i].id;
                let name = customers[i].name;
                let address = customers[i].address;
                let salary = customers[i].salary;
                console.log("id: "+id+" name: "+name+" address: "+address+" salary: "+salary);
                let row = `<tr><td>${id}</td><td>${name}</td><td>${address}</td><td>${salary}</td></tr>`;
                $("#tblCustomer").append(row);
            }
        },
        error: function (resp) {
            alert("Failed to load the customer");
        }
    });
}
$("#clearBtn").click(() => {
    $cNameTxt.val("");
    $cIdTxt.val("");
    $cAddressTxt.val("");
    $cSalaryText.val("");
});

$("#cDeleteBtn").click(() => {
    deleteCustomer();
    updateCustomerTable();
});

$("#cSearchBtn").click(function () {
    searchCustomer();
});

function saveCustomer(){
    let newCustomer = Object.assign({},customer);
    newCustomer.id = $cIdTxt.val();
    newCustomer.name = $cNameTxt.val();
    newCustomer.address = $cAddressTxt.val();
    newCustomer.salary = $cSalaryText.val();

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/check/customer",
        contentType: JSON.stringify(newCustomer),
        data: JSON.stringify(newCustomer),
        success: function (resp) {
            alert("Customer Saved");
        },
        error: function (resp) {
            alert("Failed to save the customer");
        }
    })
}

function updateCustomer(){
    let newCustomer = Object.assign({},customer);
    newCustomer.id = $cIdTxt.val();
    newCustomer.name = $cNameTxt.val();
    newCustomer.address = $cAddressTxt.val();
    newCustomer.salary = $cSalaryText.val();

    $.ajax({
        type: "PUT",
        url: "http://localhost:8080/check/customer",
        contentType: JSON.stringify(newCustomer),
        data: JSON.stringify(newCustomer),
        success: function (resp) {
            alert("Customer Updated");
        },
        error: function (resp) {
            alert("Failed to update the customer");
        }
    })
}

function deleteCustomer() {
    var customerIdToDelete = $cIdTxt.val();

    $.ajax({
        type: "DELETE",
        url: `http://localhost:8080/check/customer/${customerIdToDelete}`,
        success: function (resp) {
            alert("Customer Deleted");
            clearForm();
        },
        error: function (resp) {
            alert("Failed to delete the customer");
        }
    });
}


function searchCustomer() {
    var customerId = $cIdTxt.val();
    console.log("customer id: "+customerId);

    $.ajax({
        type: "GET",
        url: `http://localhost:8080/check/customer/${customerId}`,
        success: function (resp) {
            $cNameTxt.val(resp.name);
            $cAddressTxt.val(resp.address);
            $cSalaryText.val(resp.salary);
        },
        error: function (resp) {
            alert("Failed to find the customer");
        }
    });
}

function clearForm() {
    $cIdTxt.val("");
    $cNameTxt.val("");
    $cAddressTxt.val("");
    $cSalaryText.val("");
}

export { saveCustomer, updateCustomer ,deleteCustomer };

