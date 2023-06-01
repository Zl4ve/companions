const fromListElement = document.getElementById('from-list');
const fromInputElement = document.getElementById('source');
const toListElement = document.getElementById('to-list');
const toInputElement = document.getElementById('destination');

async function getCities(startsWith) {

    let cities = [];

    startsWith = startsWith.toLowerCase();
    startsWith = startsWith.charAt(0).toUpperCase() + startsWith.slice(1);

    await fetch("http://localhost:7070/cities?startsWith=" + startsWith)
        .then((response) => response.json())
        .then((data) => {
            cities = data.map((x) => x.name);
        });

    return cities;
}

async function showSuggestions(e) {

    let results = []
    let inputValue = e.currentTarget.value.toString();
    if (inputValue.length >= 2) {
        results = await getCities(inputValue);
    }

    if (e.target.id === 'source') {
        fromListElement.innerHTML = '';
    } else if (e.target.id === 'destination') {
        toListElement.innerHTML = '';
    }

    for (let i = 0; i < results.length; i++) {
        let item = results[i];
        console.log(item);

        if (e.target.id === 'source') {
            fromListElement.innerHTML += '<li>' + item + '</li><br>';
        } else if (e.target.id === 'destination') {
            toListElement.innerHTML += '<li>' + item + '</li><br>';
        }
    }
}

async function useSuggestion(e) {

    if (e.target.parentElement.id === 'from-list') {
        fromInputElement.value = e.target.innerText;
        fromListElement.innerHTML = '';

    } else if (e.target.parentElement.id === 'to-list') {
        toInputElement.value = e.target.innerText;
        toListElement.innerHTML = '';
    }
}

fromInputElement.addEventListener('keyup', showSuggestions);
fromListElement.addEventListener('click', useSuggestion);
toInputElement.addEventListener('keyup', showSuggestions);
toListElement.addEventListener('click', useSuggestion);
