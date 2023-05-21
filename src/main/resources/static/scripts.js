document.addEventListener("DOMContentLoaded", function() {
  const body = document.querySelector('body');
  const page = body.dataset.js;
  let username = sessionStorage.getItem("username");
  
  // -----------------SideBar responsivo----------------------------------
  $('.navbar-toggler').on('click', function () {
    $('.navbar-toggler-icon').toggleClass('open');
  });
  
	window.addEventListener('scroll', function() {
	  var scrollTop = window.pageYOffset || document.documentElement.scrollTop;
	  var sidebar = document.querySelector('#sidebar');
	  var windowHeight = window.innerHeight;
	  var sidebarHeight = sidebar.offsetHeight;
	
	  if (windowHeight > sidebarHeight) {
	    sidebar.style.height = (windowHeight + scrollTop) + 'px';
	  }
	});

  const sidebar = document.querySelector('#sidebar');
  if (sidebar) {
    window.addEventListener('load', function() {
      const screenWidth = window.innerWidth;
      if (screenWidth > 900) {
        sidebar.classList.add('show');
      }

      window.addEventListener('resize', function() {
        const screenWidth = window.innerWidth;
        if (screenWidth > 900) {
          sidebar.classList.add('show');
        } else {
          sidebar.classList.remove('show');
        }
      });
    });
  }

  $(document).ready(function() {
  const profileImgSM = $('#profile-img-sm');
  const savedImg2 = localStorage.getItem('img');
  
    if (savedImg2) {
      profileImgSM.attr('src', savedImg2);
    }
  });
  
  if (page === 'Perfil') {
    // PERFIL----------------------------------------------------------------------------------------------
    $(document).ready(function() {
      const profileImg = $('#profile-img');
      const fileInput = $('#file-input');
      const progressBar = $('#progress-bar');
      const changeProfileIcon = $('#change-profile-icon');
      const profileContainer = $('#profile-container');
      const progressText = $('#progress-text');
      const nextLevel = $('#next-level');
      const levelInfo = $('#level-info');
      const nextLevelInfo = $('#next-level-info');

      let level = 1;
      let experience = parseInt(localStorage.getItem('experience')) || 0;
      let nextLevelExperience = 100;
	
		const savedImg = localStorage.getItem('img');
		if (savedImg) {
		  profileImg.attr('src', savedImg);
		}
	
		// Obtener el nombre de usuario de la sesión actual
//	var username = sessionStorage.getItem("username");
	
	// Obtener la imagen de perfil del localStorage o usar la imagen por defecto
	 profileImg = localStorage.getItem("profileImg") || "default-profile-picture.png";
	
	// Actualizar el nombre de usuario y la imagen de perfil en el documento HTML
	document.getElementById("username").textContent = username;
	document.getElementById("profile-img").src = profileImg;

		
			// Escuchar el evento de clic en el botón de edición
	  $('#edit-btn').click(function() {
	    // Abrir una ventana emergente para ingresar el nuevo nombre de usuario
	    var newUsername = prompt('Introduce el nuevo nombre de usuario:');
	    
	    // Si se ingresó un nuevo nombre de usuario
	    if (newUsername) {
	      // Realizar una solicitud AJAX al backend para actualizar el nombre de usuario
			// Realizar una solicitud AJAX al backend para actualizar el nombre de usuario
			// Realizar una solicitud AJAX al backend para actualizar el nombre de usuario
			$.ajax({
			  url: '/update-username', // La URL del endpoint en tu controlador de Spring Boot
			  type: 'POST',
			  data: newUsername, // Enviar solo el valor del nuevo nombre de usuario como una cadena de texto
			  processData: false, // Evitar que jQuery procese los datos
			  contentType: false, // Evitar que jQuery configure automáticamente el encabezado Content-Type
			  success: function(response) {
			    // La solicitud se realizó con éxito, puedes realizar alguna acción adicional si es necesario
			    console.log('Nombre de usuario actualizado con éxito' + newUsername);
			  },
			  error: function(error) {
			    // Ocurrió un error durante la solicitud AJAX, puedes manejarlo de acuerdo a tus necesidades
			    console.error('Error al actualizar el nombre de usuario:', error);
			  }
			});
	    }
	  });
		
      changeProfileIcon.click(function() {
        fileInput.click();
      });

      fileInput.change(function() {
        const file = fileInput[0].files[0];
        if (file) {
          const reader = new FileReader();
          reader.onload = function(e) {
            profileImg.attr('src', e.target.result);
            localStorage.setItem('img', e.target.result);
          }
          reader.readAsDataURL(file);
        }
      });

      changeProfileIcon.mouseenter(function() {
        profileImg.css('opacity', '0.5');
      });

      profileContainer.mouseleave(function() {
        profileImg.css('opacity', '1');
      });

      let progress = 0;
      let intervalId;

      function updateProgress() {
        progressBar.css('width', `${progress}%`);
        const currentLevel = Math.floor(progress / nextLevelExperience) + 1;
        progressText.text(`${progress}% - Nivel ${currentLevel} (${experience}/${nextLevelExperience})`);
        if (currentLevel === 10) {
          nextLevel.text('¡Has llegado al máximo nivel!');
          clearInterval(intervalId);
        } else {
          nextLevel.text(`Nivel ${currentLevel + 1}`);
        }
      }

      function updateExperienceInfo() {
        levelInfo.text(`Nivel(${level})`);
        experience.text(`(${experience}/${nextLevelExperience})`);
        updateNextLevelInfo();
      }

      function updateNextLevelInfo() {
        if (level === 10) {
          nextLevelInfo.text('¡Has llegado al máximo nivel!');
        } else {
          nextLevelExperience = 100 * (level + 1);
          nextLevelInfo.text(`Siguiente nivel: ${level + 1} (${nextLevelExperience} XP)`);
        }
      }

      function updateExperience(value) {
        if (value < 0 || value > 100) {
          return;
        }
        experience += value;
        localStorage.setItem('experience', experience);
        progress = (experience / nextLevelExperience) * 100;
        intervalId = setInterval(updateProgress, 10);

        while (experience >= nextLevelExperience) {
          level++;
          experience -= nextLevelExperience;
          nextLevelExperience *= 2;
          updateExperienceInfo();
        }
      }

      updateExperienceInfo();
      updateNextLevelInfo();
    });
  } else if (page === 'Cartera') {
    // CARTERA------------------------------------------------------------------------------------------------------
    if (isNaN(balance)) {
      balance = 0;
    }
    document.getElementById("balance").textContent = balance.toFixed(2);

    const buttons = document.querySelectorAll(".btn");
    buttons.forEach(button => {
      button.addEventListener("click", () => {
        const amount = parseFloat(prompt("¿Cuánto dinero quieres retirar?"));
        if (isNaN(amount) || amount <= 0 || amount > balance) {
          alert("Cantidad inválida");
          return;
        }
        balance -= amount;
        localStorage.setItem("balance", balance);
        document.getElementById("balance").textContent = balance.toFixed(2);
        alert(`Se han retirado $${amount.toFixed(2)} a ${button.textContent.trim()}`);
      });
    });
  } else if (page === 'Invitaciones') {
    // INVITACIONES ----------------------------------------------------------------------------------------------------
		$(document).ready(function() {
		  $.ajax({
		    url: '/obtener-codigo-referencia',
		    type: 'GET',
		    success: function(response) {
		      var referralCode = response;
		      var refLink = 'https://mi-sitio.com/?ref=' + referralCode;
		      console.log(refLink);
		      $("#refLink").attr('href', refLink).text(refLink);
		    },
		    error: function(xhr, status, error) {
		      console.error(error);
		    }
		  });
		});

      
    $("#count").text(localStorage.getItem("count"));
    $("#balance").text(calcularSaldo());

    function calcularSaldo() {
      var total = 0;
      for (var i = 0; i < localStorage.length; i++) {
        var key = localStorage.key(i);
        if (key.includes("balance")) {
          var balance = parseInt(localStorage.getItem(key));
          if (!isNaN(balance)) {
            total += balance;
          }
        }
      }
      return total;
    }
  } else if (page === 'Sorteo') {
    // SORTEO --------------------------------------------------------------------------------------------------------------
    $(document).ready(function() {
      function actualizarPremio() {
        var premio = (Math.random() * (5 - 1) + 1).toFixed(2);
        $("#premio").text(premio + "€");
    
        // Realizar una solicitud al backend para obtener un participante aleatorio y actualizar la lista de ganadores
        $.get('/obtener-ganador', function(response) {
          var usernameGanador = response.ganador;
          var cantidadGanada = premio;
          var idGanador = response.idGanador;
    
          // Realizar una solicitud al backend para agregar el ganador a la tabla de ganadores en la base de datos
			$.post('/agregar-ganador/' + idGanador + '/' + cantidadGanada, function() {
			  console.log('Ganador agregado a la tabla de ganadores');
			});

          // Reiniciar la lista de participantes en el frontend
          $("#participantes").empty();
    
          // Actualizar el balance del usuario ganador en la base de datos
          // Realizar una solicitud PUT o PATCH al backend para actualizar el balance del usuario ganador
          $.ajax({
            url: '/actualizar-balance/' + idGanador,
            type: 'PUT',
            data: JSON.stringify({ cantidadGanada: cantidadGanada }),
            contentType: 'application/json', // Especifica el tipo de contenido como JSON
            success: function(response) {
              console.log('Balance actualizado correctamente');
            },
            error: function(error) {
              console.error('Error al actualizar el balance');
            }
          });
        });
      }
    
		function countdown() {
		  var now = new Date().getTime();
		  var distance = 60000 - (now % 60000);
		  var hours = Math.floor(distance / (1000 * 60 * 60));
		  var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
		  var seconds = Math.floor((distance % (1000 * 60)) / 1000);
		  $("#countdown").text(hours + "h " + minutes + "m " + seconds + "s");
		  console.log(distance);
		  if (distance <= 1000) {
		    actualizarPremio();
		    deleteAll();
		  }
		
		  // Esperar hasta el próximo segundo para llamar nuevamente a countdown()
		  setTimeout(countdown, 1000 - (new Date().getTime() % 1000));
		}
      countdown();
    
      function agregarUsuario() {
        const h4Element = document.getElementById("miH4");
        const contenidoH4 = h4Element.textContent;
    
        var imagenUsuario = localStorage.getItem('img');
        var username = sessionStorage.getItem("username"); // Obtener el nombre de usuario del sessionStorage
        $.post('/agregar-usuario', { username: username }, function(response) {
          // Si la petición es exitosa, puedes agregar el usuario a la lista en el frontend.
          var nuevoUsuario = $("<li class='list-group-item d-flex justify-content-between align-items-center'><img class='rounded-circle mr-2' id='part' src='https://via.placeholder.com/50'/><span>" + contenidoH4 + "</span></li>");
          $("#participantes").append(nuevoUsuario);
        });
      }
    
      function deleteAll() {
        $.ajax({
          url: '/renew-parts',
          type: 'DELETE',
          success: function(response) {
            // Manejar la respuesta exitosa aquí
          },
          error: function(xhr) {
            // Manejar el error aquí
          }
        });
      }
    
      $("#participar-btn").click(function() {
        agregarUsuario();
      });
    });
  }
});
