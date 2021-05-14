$(document).on("click", "#btnSave", function(event)
{
// Clear alerts---------------------
 	$("#alertSuccess").text("");
 	$("#alertSuccess").hide();
 	$("#alertError").text("");
 	$("#alertError").hide();
 	
 	var status = validateProjectForm();
if (status != true)
 {
 $("#alertError").text(status);
 $("#alertError").show();
 return;
 }
// If valid------------------------
var type = ($("#hidProjectIDSave").val() == "") ? "POST" : "PUT";
 $.ajax(
 {
 url : "ProjectsAPI",
 type : type,
 data : $("#formProject").serialize(),
 dataType : "text",
 complete : function(response, status)
 {
 onProjectSaveComplete(response.responseText, status);
 }
 });
});



function onProjectSaveComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully saved.");
 $("#alertSuccess").show();
 $("#divProjectsGrid").html(resultSet.data);
 } else if (resultSet.status.trim() == "error")
 {
 $("#alertError").text(resultSet.data);
 $("#alertError").show();
 }
 } else if (status == "error")
 {
 $("#alertError").text("Error while saving.");
 $("#alertError").show();
 } else
 {
 $("#alertError").text("Unknown error while saving..");
 $("#alertError").show();
 }
$("#hidProjectIDSave").val("");
$("#formProject")[0].reset();
}


$(document).on("click", ".btnUpdate", function(event)
		{
		$("#hidProjectIDSave").val($(this).data("projid"));
		 $("#projCode").val($(this).closest("tr").find('td:eq(0)').text());
		 $("#projName").val($(this).closest("tr").find('td:eq(1)').text());
		 $("#projPrice").val($(this).closest("tr").find('td:eq(2)').text());
		 $("#projDesc").val($(this).closest("tr").find('td:eq(3)').text());
		});


$(document).on("click", ".btnRemove", function(event)
		{
		 $.ajax(
		 {
		 url : "ProjectsAPI",
		 type : "DELETE",
		 data : "projID=" + $(this).data("projid"),
		 dataType : "text",
		 complete : function(response, status)
		 {
		 onProjectDeleteComplete(response.responseText, status);
		 }
		 });
		});



function onProjectDeleteComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully deleted.");
 $("#alertSuccess").show();
 $("#divProjectsGrid").html(resultSet.data);
 } else if (resultSet.status.trim() == "error")
 {
 $("#alertError").text(resultSet.data);
 $("#alertError").show();
 }
 } else if (status == "error")
 {
 $("#alertError").text("Error while deleting.");
 $("#alertError").show();
 } else
 {
 $("#alertError").text("Unknown error while deleting..");
 $("#alertError").show();
 }
}




//CLIENT-MODEL================================================================
function validateProjectForm()
{
// CODE
if ($("#projCode").val().trim() == "")
 {
 return "Insert project Code.";
 }
// NAME
if ($("#projName").val().trim() == "")
 {
 return "Insert project Name.";
 }
// PRICE-------------------------------
if ($("#projPrice").val().trim() == "")
 {
 return "Insert project Price.";
 }
// is numerical value
var tmpPrice = $("#projPrice").val().trim();
if (!$.isNumeric(tmpPrice))
 {
 return "Insert a numerical value for project Price.";
 }
// convert to decimal price
 $("#projPrice").val(parseFloat(tmpPrice).toFixed(2));
// DESCRIPTION------------------------
if ($("#projDesc").val().trim() == "")
 {
 return "Insert project Description.";
 }
return true;
}
 	