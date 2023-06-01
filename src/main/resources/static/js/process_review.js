
function showReviewForm(formId) {
    const formElement = document.getElementById(formId);

    if (formElement.style.display === 'none') {
        formElement.style.display = 'block';
    } else {
        formElement.style.display = 'none';
    }
}


async function sendReviewData(event) {

    event.preventDefault();

    const driveId = event.target.querySelector("input.drive-id").value;
    const rateInput = event.target.querySelector("input.rate").value;
    const textInput = event.target.querySelector("input.text").value;

    await fetch('http://localhost:7070/review/add/' + driveId, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': event.target.querySelector("input._csrf").value,
        },
        body: JSON.stringify({"rate": rateInput, "text": textInput})
    }).then((response) => response.json())
        .then((data) => {
            if (data.hasOwnProperty('message')) {
                const reviewError = document.getElementById("review-error" + driveId);
                reviewError.innerHTML = data.message;
            } else {

            }
        });
}