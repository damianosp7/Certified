$('.my-2.table.delBtn').on('click', function (event){
    event.preventDefault();
    let href = $(this).attr('href');
    $('#myModal #delRef').attr('href', href);
    $('#myModal').showModal();
});