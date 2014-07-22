/**
 * 
 */

jQuery(document).ready(
		function() {
			if (typeof jQuery !== 'undefined') {
				console.log('jQuery Loaded');
			} else {
				console.log('not loaded yet');
			}

			jQuery(function() {
				jQuery("#loginForAASpille").text("Logg inn for å spille");
				jQuery("#SayHello").click(
						function() {
							alert('Hello clicked!');
							var kallenavn = jQuery("#txtBrukerNavn").val();
							var serviceURL = "http://localhost:8080/service/hello-world";
							if (kallenavn.length > 0) {
								serviceURL = serviceURL + "?name=" + kallenavn;
							}
							
							
							jQuery.getJSON(
									serviceURL,
									function(result) {
										jQuery("#antallKall").text(result.id);
										jQuery("#HelloWorld").text(result.content);
									});
						});
			});
		});