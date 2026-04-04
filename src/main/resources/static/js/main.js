document.addEventListener("DOMContentLoaded", function () {
    setupProfileDropdown();
    setupWishlistButtons();
    setupVideoHoverCards();
});

function setupProfileDropdown() {
    const profileBtn = document.getElementById("profileDropdownBtn");
    const profileMenu = document.getElementById("profileDropdownMenu");

    if (!profileBtn || !profileMenu) {
        return;
    }

    profileBtn.addEventListener("click", function (e) {
        e.stopPropagation();
        profileMenu.classList.toggle("show");
    });

    document.addEventListener("click", function (e) {
        if (!profileMenu.contains(e.target) && !profileBtn.contains(e.target)) {
            profileMenu.classList.remove("show");
        }
    });
}

function setupWishlistButtons() {
    const wishlistButtons = document.querySelectorAll(".wishlist-btn");
    if (!wishlistButtons.length) {
        return;
    }

    const csrfTokenMeta = document.querySelector('meta[name="_csrf"]');
    const csrfHeaderMeta = document.querySelector('meta[name="_csrf_header"]');

    const csrfToken = csrfTokenMeta ? csrfTokenMeta.getAttribute("content") : null;
    const csrfHeader = csrfHeaderMeta ? csrfHeaderMeta.getAttribute("content") : null;

    wishlistButtons.forEach(button => {
        button.addEventListener("click", function () {
            const gameId = this.getAttribute("data-game-id");
            if (!gameId) {
                return;
            }

            const headers = {
                "X-Requested-With": "XMLHttpRequest"
            };

            if (csrfToken && csrfHeader) {
                headers[csrfHeader] = csrfToken;
            }

            fetch(`/wishlist/toggle/${gameId}`, {
                method: "POST",
                headers: headers
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Wishlist request failed");
                    }
                    return response.json();
                })
                .then(data => {
                    const icon = button.querySelector("i");

                    if (data && data.favorited) {
                        if (icon) {
                            icon.classList.remove("bi-heart");
                            icon.classList.add("bi-heart-fill");
                        }
                        button.classList.add("active");
                    } else {
                        if (icon) {
                            icon.classList.remove("bi-heart-fill");
                            icon.classList.add("bi-heart");
                        }
                        button.classList.remove("active");
                    }

                    const wishlistCard = button.closest(".wishlist-card");
                    const isWishlistPage = document.querySelector(".wishlist-list") !== null;

                    if (wishlistCard && isWishlistPage && data && data.favorited === false) {
                        wishlistCard.style.opacity = "0";
                        wishlistCard.style.transform = "translateY(-8px)";

                        setTimeout(() => {
                            wishlistCard.remove();

                            const remainingCards = document.querySelectorAll(".wishlist-card");
                            const wishlistList = document.querySelector(".wishlist-list");
                            const emptyState = document.querySelector(".wishlist-empty-state");

                            if (remainingCards.length === 0 && emptyState) {
                                if (wishlistList) {
                                    wishlistList.style.display = "none";
                                }
                                emptyState.style.display = "flex";
                            }
                        }, 220);
                    }
                })
                .catch(error => {
                    console.error("Wishlist toggle error:", error);
                    alert("Wishlist action failed. Check console.");
                });
        });
    });
}

function setupVideoHoverCards() {
    const videoCards = document.querySelectorAll(".video-card");
    if (!videoCards.length) {
        return;
    }

    videoCards.forEach(card => {
        const video = card.querySelector(".thumb-video");
        if (!video) {
            return;
        }

        card.addEventListener("mouseenter", () => {
            try {
                video.currentTime = 0;
                const playPromise = video.play();
                if (playPromise !== undefined) {
                    playPromise.catch(() => {});
                }
            } catch (e) {
                console.warn("Video play failed:", e);
            }
        });

        card.addEventListener("mouseleave", () => {
            try {
                video.pause();
                video.currentTime = 0;
            } catch (e) {
                console.warn("Video pause failed:", e);
            }
        });
    });
}