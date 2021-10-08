/*$(function(){
    var btnSubmit = $("#commentfrm").find("input[type=submit]");

    btnSubmit.on("click", function(){
       $.ajax({
           url: 'contries//comment" %}',
           type: "POST",
           data: {
                name: $("#your_comment").val() //obter conteudo da caixa de texto
           },
           beforeSend: function(){a
               console.log("eu vou enviar o nome " + $("#your_comment").val());
           }
       }).done(function (data){ //quando o pedido AJAX corre bem
           console.log("enviei com sucesso o nome " + data.name);
       }).fail(function(data, info){ //quando o pedido AJAX falha
           console.log("obtive o erro de envio " + info);
       }).always(function(){
           $("#your_comment").val(""); //limpar o conteúdo da caixa de texto
       });
    });
});*/