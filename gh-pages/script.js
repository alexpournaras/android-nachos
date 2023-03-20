document.addEventListener('DOMContentLoaded', function() {
	
	const burgerMenu = document.getElementById('burger-menu');
	const menuLinks = document.getElementById('menu-links');
	const burgerIcon = burgerMenu.querySelector('i');

	burgerMenu.addEventListener('click', function () {
		menuLinks.classList.toggle("show-menu");
		burgerIcon.classList.toggle('fa-times');
		burgerIcon.classList.toggle('fa-bars');
	});

});