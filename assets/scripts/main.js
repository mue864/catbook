const signUp = document.getElementById('buttonSignUp');
const container = document.querySelector('.container');
const formSign = document.querySelector('.form-sign-up');
const signIn = document.querySelector('.sign-in-link');
const creatAcc = document.querySelector('.sign');
const messageContainer = document.getElementById('messageContainer');

signUp.addEventListener('click', () => {
    container.classList.toggle('show-signup');
});

signIn.addEventListener('click', () => {
    container.classList.toggle('show-signin');
    container.classList.remove('show-signup');
    container.classList.remove('show-signin');
});

// Move these assignments inside the event listener
creatAcc.addEventListener('click', () => {
    const userName = document.getElementById('userName').value;
    const password = document.getElementById('password').value;
    const passConfirm = document.getElementById('passConfirm').value;

    if (passConfirm !== password) {
        
        displayMessage('Passwords do not match!');
        highlightInput('password');
        highlightInput('passConfirm');
        return false;
    } else {
        hideMessage();
        resetInputStyles('password');
        resetInputStyles('passConfirm');
        return true;
    }
});

function displayMessage(message) {
    messageContainer.innerHTML = message;
    messageContainer.classList.add('show-message');
}

function hideMessage() {
    messageContainer.innerHTML = '';
    messageContainer.classList.remove('show-message');
}

function highlightInput(inputId) {
    const inputElement = document.getElementById(inputId);
    inputElement.classList.add('input-error');
}

function resetInputStyles(inputId) {
    const inputElement = document.getElementById(inputId);
    inputElement.classList.remove('input-error');
}
