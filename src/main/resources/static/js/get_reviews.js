fetch('http://localhost:7070/reviews/' + document.getElementById("user-id").value)
    .then((response) => response.json())
    .then((data) => {
        if (data.hasOwnProperty("message")) {
            document.getElementById("reviews").innerHTML += data.message;
        } else {
            let reviewContent = document.getElementById("reviews").innerHTML;
            data.forEach(obj => {
                let lt = /</g,
                    gt = />/g,
                    ap = /'/g,
                    ic = /"/g;
                reviewContent += '<div class="review">';
                reviewContent += '<div class="left-review-part">';
                reviewContent += '<div class="review-author">';
                if (obj.author.details.avatarReference != null) {
                    reviewContent += '<img src="/image/' + obj.author.details.avatarReference + '" class="review-author-avatar">';
                } else {
                    reviewContent += '<img src="/image/emptyavatar.png" class="review-author-avatar">';
                }
                reviewContent += '<span class="review-author-name"><a href="/profile/' + obj.author.id + '">' + obj.author.username.replace(lt, "&lt;").replace(gt, "&gt;").replace(ap, "&#39;").replace(ic, "&#34;") + '</a></span>';
                reviewContent += '</div>';
                reviewContent += '<div class="review-content">';
                reviewContent += '<p class="review-text">' + obj.reviewText.replace(lt, "&lt;").replace(gt, "&gt;").replace(ap, "&#39;").replace(ic, "&#34;") + '</p>';
                reviewContent += '</div>';
                reviewContent += '</div>';
                reviewContent += '<div class="right-review-part">';
                reviewContent += '<span class="review-rate">Rate: ' + obj.rate + '</span>';
                reviewContent += '</div>';
                reviewContent += '</div>';
            });

            document.getElementById("reviews").innerHTML = reviewContent;
        }
    });