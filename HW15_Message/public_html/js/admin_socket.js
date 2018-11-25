var ws;

init = function () {
    ws = new WebSocket("ws://localhost:8090/users/admin");


    ws.onopen = function (event) {
           var message = JSON.stringify( { 'cmd'  : "read_all"  } );
           ws.send(message);
    }


    ws.onmessage = function (event) {
         var table = document.getElementById("usrtab");
         var newObj = JSON.parse(event.data);
         var i;

        for (i = 1; i < table.rows.length;) {
          table.deleteRow(i);
        }

         newObj.forEach(function(item, x, newObj) {

             var row   = table.insertRow();
             var cell1 = row.insertCell(0);
             var cell2 = row.insertCell(1);
             var cell3 = row.insertCell(2);
             var cell4 = row.insertCell(3);
             var cell5 = row.insertCell(4);

             cell1.innerHTML = item.id;
             cell2.innerHTML = item.name;
             cell3.innerHTML = item.age;
             cell4.innerHTML = item.address;
             cell5.innerHTML = item.phones;
             });

    }
    ws.onclose = function (event) {

    }
};

function addUser() {

    var userNameField  = document.getElementById("add_name");
    var ageField       = document.getElementById("add_age");
    var addressField   = document.getElementById("add_address");
    var phonesField    = document.getElementById("add_phones");





    var message = JSON.stringify( {  'cmd'    :  "add",
                                     'name'   :  userNameField.value ,
                                     'age'    :  ageField.value,
                                     'address':  addressField.value,
                                     'phones':   phonesField.value
                                   } );

    ws.send(message);
    userNameField.value = '';
    ageField.value = '';
    addressField.value = '';
    phonesField.value = '';

}

function findUser() {

var user_id  = document.getElementById("find_user");

if (user_id.value == "") {
                            alert("Please fill id!");
                            return;
                         }

  var message = JSON.stringify( { 'cmd' : "find" , 'id' : user_id.value } );
  ws.send(message);
  user_id.value = '';
}

