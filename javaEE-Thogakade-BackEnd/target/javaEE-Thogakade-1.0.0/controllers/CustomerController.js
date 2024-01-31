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
        url: "http://localhost:8080/app/customer",
        success: function (customers) {
            for(let i in customers){
                let id = customers[i].id;
                let name = customers[i].name;
                let address = customers[i].address;
                let salary = customers[i].salary;

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
    const searchValue = $("#cSearchTxt").val();

    if (searchValue.trim() === "") {
        alert("Please enter a valid Customer ID to search.");
        return;
    }

    const customer = Customers.find((c) => c.id === searchValue);

    if (customer) {
        $cNameTxt.val(customer.name);
        $cIdTxt.val(customer.id);
        $cAddressTxt.val(customer.address);
        $cSalaryText.val(customer.salary);
    } else {
        alert("Customer not found.");
    }
});

function saveCustomer(){
    let newCustomer = Object.assign({},customer);
    newCustomer.id = $cIdTxt.val();
    newCustomer.name = $cNameTxt.val();
    newCustomer.address = $cAddressTxt.val();
    newCustomer.salary = $cSalaryText.val();

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/app/customer",
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
        url: "http://localhost:8080/app/customer",
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
        url: `http://localhost:8080/app/customer/${customerIdToDelete}`,
        success: function (resp) {
            alert("Customer Deleted");
            clearForm();
        },
        error: function (resp) {
            alert("Failed to delete the customer");
        }
    });
}

export { saveCustomer, updateCustomer ,deleteCustomer };

