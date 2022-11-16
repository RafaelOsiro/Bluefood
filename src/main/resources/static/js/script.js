function isNumberKey(evt) {
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode == 8 || (charCode >= 48 && charCode <= 57) || (charCode >= 96 && charCode <= 105)) {
        return true;
    }   
    return false;
}