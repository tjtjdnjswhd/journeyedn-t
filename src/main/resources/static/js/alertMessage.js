{
    let queryStrings = new URLSearchParams(location.search);
    let errorMessage = queryStrings.get('message');
    if (errorMessage) {
        alert(errorMessage);
    }
}