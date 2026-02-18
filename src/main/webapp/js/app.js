document.addEventListener('DOMContentLoaded', () => {
    const listaMascotas = document.getElementById('lista-mascotas');
    const modalAdopcion = document.getElementById('modal-adopcion');
    const modalRegistrar = document.getElementById('modal-registrar');
    const formAdopcion = document.getElementById('form-adopcion');
    const formRegistrar = document.getElementById('form-registrar');
    const nombreMascotaModal = document.getElementById('nombre-mascota-modal');
    const idMascotaInput = document.getElementById('id-mascota');
    const btnNuevaMascota = document.getElementById('btn-nueva-mascota');

    // Cargar mascotas al iniciar
    cargarMascotas();
    cargarAdopciones();

    // Botón para abrir modal de registrar mascota
    btnNuevaMascota.addEventListener('click', () => {
        modalRegistrar.style.display = 'flex';
    });

    // Cerrar modales
    document.querySelectorAll('.close').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const modalId = e.target.getAttribute('data-modal');
            document.getElementById(modalId).style.display = 'none';
            if (modalId === 'modal-adopcion') formAdopcion.reset();
            if (modalId === 'modal-registrar') formRegistrar.reset();
        });
    });

    window.onclick = (event) => {
        if (event.target === modalAdopcion) {
            modalAdopcion.style.display = 'none';
            formAdopcion.reset();
        }
        if (event.target === modalRegistrar) {
            modalRegistrar.style.display = 'none';
            formRegistrar.reset();
        }
    };

    // Función para cargar mascotas
    async function cargarMascotas() {
        try {
            const response = await fetch('api/mascotas');
            if (!response.ok) throw new Error('Error al conectar con el servidor');

            const mascotas = await response.json();
            renderizarMascotas(mascotas);
        } catch (error) {
            console.error('Error:', error);
            listaMascotas.innerHTML = '<p class="loading">Error al cargar las mascotas. Intente nuevamente.</p>';
        }
    }

    // Renderizar tarjetas de mascotas
    function renderizarMascotas(mascotas) {
        listaMascotas.innerHTML = '';

        if (mascotas.length === 0) {
            listaMascotas.innerHTML = '<p class="loading">No hay mascotas disponibles para adopción.</p>';
            return;
        }

        mascotas.forEach(mascota => {
            const card = document.createElement('div');
            card.className = 'card';

            const especieNombre = mascota.id_especie === 1 ? 'Perro' : mascota.id_especie === 2 ? 'Gato' : 'Conejo';

            card.innerHTML = `
                <div class="card-header">
                    <h3 class="card-title">${mascota.nombre}</h3>
                    <span class="card-tag">Disponible</span>
                </div>
                <div class="card-info">
                    <span><strong>Especie:</strong> ${especieNombre}</span>
                    <span><strong>Raza:</strong> ${mascota.raza || 'Sin especificar'}</span>
                    <span><strong>Edad:</strong> ${mascota.edad} año${mascota.edad !== 1 ? 's' : ''}</span>
                </div>
                <p class="card-desc">${mascota.descripcion || 'Sin descripción disponible.'}</p>
                <button class="btn-card" onclick="abrirModalAdopcion(${mascota.id_mascota}, '${mascota.nombre}')">Adoptar</button>
            `;
            listaMascotas.appendChild(card);
        });
    }

    // Abrir modal de adopción
    window.abrirModalAdopcion = (id, nombre) => {
        idMascotaInput.value = id;
        nombreMascotaModal.textContent = nombre;
        modalAdopcion.style.display = 'flex';
    };

    // Registrar nueva mascota
    formRegistrar.addEventListener('submit', async (e) => {
        e.preventDefault();

        const btnSubmit = formRegistrar.querySelector('.btn-submit');
        const originalText = btnSubmit.textContent;
        btnSubmit.textContent = 'Registrando...';
        btnSubmit.disabled = true;

        const nombre = document.getElementById('reg-nombre').value.trim();
        const edad = parseInt(document.getElementById('reg-edad').value);
        const raza = document.getElementById('reg-raza').value.trim();
        const descripcion = document.getElementById('reg-descripcion').value.trim();

        // Validaciones adicionales
        if (nombre.length < 2 || nombre.length > 50) {
            alert('El nombre debe tener entre 2 y 50 caracteres.');
            btnSubmit.textContent = originalText;
            btnSubmit.disabled = false;
            return;
        }

        if (!/^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+$/.test(nombre)) {
            alert('El nombre solo puede contener letras y espacios.');
            btnSubmit.textContent = originalText;
            btnSubmit.disabled = false;
            return;
        }

        if (edad < 0 || edad > 30) {
            alert('La edad debe estar entre 0 y 30 años.');
            btnSubmit.textContent = originalText;
            btnSubmit.disabled = false;
            return;
        }

        const datos = {
            nombre: nombre,
            id_especie: parseInt(document.getElementById('reg-especie').value),
            raza: raza || null,
            edad: edad,
            descripcion: descripcion || null
        };

        try {
            const response = await fetch('api/mascotas', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(datos)
            });

            const result = await response.json();

            if (response.ok) {
                alert('Mascota registrada exitosamente.');
                modalRegistrar.style.display = 'none';
                formRegistrar.reset();
                cargarMascotas();
            } else {
                alert('Error: ' + (result.error || 'No se pudo registrar la mascota.'));
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Error de conexión. Intente nuevamente.');
        } finally {
            btnSubmit.textContent = originalText;
            btnSubmit.disabled = false;
        }
    });

    // Enviar solicitud de adopción
    formAdopcion.addEventListener('submit', async (e) => {
        e.preventDefault();

        const btnSubmit = formAdopcion.querySelector('.btn-submit');
        const originalText = btnSubmit.textContent;
        btnSubmit.textContent = 'Enviando...';
        btnSubmit.disabled = true;

        const nombre = document.getElementById('nombre').value.trim();
        const telefono = document.getElementById('telefono').value.trim();
        const email = document.getElementById('email').value.trim();

        // Validaciones adicionales
        if (nombre.length < 3 || nombre.length > 100) {
            alert('El nombre debe tener entre 3 y 100 caracteres.');
            btnSubmit.textContent = originalText;
            btnSubmit.disabled = false;
            return;
        }

        if (!/^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+$/.test(nombre)) {
            alert('El nombre solo puede contener letras y espacios.');
            btnSubmit.textContent = originalText;
            btnSubmit.disabled = false;
            return;
        }

        if (!/^[0-9\-\+\s]{7,15}$/.test(telefono)) {
            alert('El teléfono debe tener entre 7 y 15 dígitos.');
            btnSubmit.textContent = originalText;
            btnSubmit.disabled = false;
            return;
        }

        const datos = {
            id_mascota: parseInt(idMascotaInput.value),
            nombre_adoptante: nombre,
            telefono: telefono,
            email: email || ''
        };

        try {
            const response = await fetch('api/adopciones', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(datos)
            });

            const result = await response.json();

            if (response.ok) {
                alert('¡Solicitud de adopción registrada exitosamente!');
                modalAdopcion.style.display = 'none';
                formAdopcion.reset();
                cargarMascotas();
                cargarAdopciones();
            } else {
                alert('Error: ' + (result.error || 'No se pudo procesar la solicitud.'));
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Error de conexión. Intente nuevamente.');
        } finally {
            btnSubmit.textContent = originalText;
            btnSubmit.disabled = false;
        }
    });

    // Función para cargar adopciones
    async function cargarAdopciones() {
        const listaAdopciones = document.getElementById('lista-adopciones');

        try {
            const response = await fetch('api/adopciones');
            if (!response.ok) throw new Error('Error al conectar con el servidor');

            const adopciones = await response.json();

            if (adopciones.length === 0) {
                listaAdopciones.innerHTML = '<p class="loading">No hay adopciones registradas aún.</p>';
                return;
            }

            let tabla = `
                <table class="tabla-adopciones">
                    <thead>
                        <tr>
                            <th>Mascota</th>
                            <th>Adoptante</th>
                            <th>Teléfono</th>
                            <th>Email</th>
                            <th>Fecha</th>
                        </tr>
                    </thead>
                    <tbody>
            `;

            adopciones.forEach(a => {
                const fecha = new Date(a.fecha_adopcion).toLocaleDateString('es-ES');
                tabla += `
                    <tr>
                        <td><strong>${a.nombre_mascota}</strong> (${a.raza || 'Sin especificar'})</td>
                        <td>${a.nombre_adoptante}</td>
                        <td>${a.telefono}</td>
                        <td>${a.email || 'No proporcionado'}</td>
                        <td class="fecha">${fecha}</td>
                    </tr>
                `;
            });

            tabla += `
                    </tbody>
                </table>
            `;

            listaAdopciones.innerHTML = tabla;
        } catch (error) {
            console.error('Error:', error);
            listaAdopciones.innerHTML = '<p class="loading">Error al cargar las adopciones.</p>';
        }
    }
});
