document.addEventListener("DOMContentLoaded", function () {
    const trailerButtons = document.querySelectorAll(".trailer-btn");
    const trailerModal = document.getElementById("trailerModal");
    const trailerFrame = document.getElementById("trailerFrame");
    const closeTrailer = document.getElementById("closeTrailer");
    const trailerOverlay = document.getElementById("trailerOverlay");

    trailerButtons.forEach(button => {
        button.addEventListener("click", function () {
            const trailerUrl = this.getAttribute("data-trailer");

            if (trailerModal && trailerFrame && trailerUrl) {
                trailerFrame.src = trailerUrl + "?autoplay=1";
                trailerModal.classList.add("active");
                document.body.style.overflow = "hidden";
            }
        });
    });

    function closeModal() {
        if (trailerModal && trailerFrame) {
            trailerModal.classList.remove("active");
            trailerFrame.src = "";
            document.body.style.overflow = "auto";
        }
    }

    if (closeTrailer) {
        closeTrailer.addEventListener("click", closeModal);
    }

    if (trailerOverlay) {
        trailerOverlay.addEventListener("click", closeModal);
    }
});