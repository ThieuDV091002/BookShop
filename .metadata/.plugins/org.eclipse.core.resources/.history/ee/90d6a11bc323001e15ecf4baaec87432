var confirmText = document.getElementById('confirmText')
var confirmModal = document.getElementById('confirmModal')
var yesButton
var noButton

$(document).ready(function() {
	confirmText = $("#confirmText")
	confirmModelDialog = $("#confirmModal")
	yesButton = $("#yesButton")
	noButton = $("#noButton")
	
	$(".linkUpdateStatus").on("click", function(e){
		e.preventDefault()
		link = $(this)
		showUpdateConfirm(link)
	})
	
	$("#noButton").on("click", function(e){
		e.preventDefault()
		confirmModal.classList.add('d-none')
	})
	
	addEventHandlerForYesButton()
})

function showUpdateConfirm(link){
	IDorder = link.attr("IDorder")
	status = link.attr("status")
	yesButton.attr("href", link.attr("href"))
	
	confirmText.text("Bạn có chắc chắn muốn cập nhật trạng thái của đơn hàng ID " + IDorder + " sang " + status + " không?")
	confirmModal.classList.remove('d-none')
}

function addEventHandlerForYesButton(){
	yesButton.click(function(e){
		e.preventDefault()
		sendRequestUpdate($(this))
	})
}

function sendRequestUpdate(button){
	requestURL = button.attr("href")
	
	$.ajax({
		type: 'POST',
		url: requestURL,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(response) {
		updateStatusColor(response.IDorder, response.status);	
		console.log(response);
		confirmModal.classList.add('d-none')
	})
}

function updateStatusColor(IDorder, status){
	link = $("#link" + status + IDorder)
	link.replaceWith("<span class='blue'>" + status + "</span>")
}