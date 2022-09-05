
function copyAccessText(elementID) {
    let element = document.getElementById(elementID);
    // let elementText = element.textContent;
    copyText(element);
    return false;
}

function copyText(textAreaElement) {
    if (typeof navigator.clipboard !== 'undefined') {
        navigator.clipboard.writeText(textAreaElement.textContent);
    } else {
        textAreaElement.focus();
        return new Promise((res, rej) => {
            document.execCommand('copy') ? res() : rej();
        });
    }

}
