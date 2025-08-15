console.log('Products page JavaScript loaded');

// Add to Basket function
function addToBasket(productId, quantity = 1) {
    console.log('Adding product to basket:', productId);

    // Find the button and show loading state
    const button = document.querySelector(`[data-product-id="${productId}"]`);
    const originalText = button ? button.innerHTML : '';

    if (button) {
        button.disabled = true;
        button.innerHTML = '⏳ Adding...';
    }

    // Make the request to your backend
    fetch(`/basket/add/${productId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `quantity=${quantity}`
    })
        .then(response => {
            console.log('Response status:', response.status);

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log('Response data:', data);

            if (data.success) {
                showNotification('✅ Item added to basket successfully!', 'success');
                updateBasketCount(data.basketCount);
            } else {
                throw new Error(data.message || 'Failed to add item to basket');
            }
        })
        .catch(error => {
            console.error('Error adding to basket:', error);
            showNotification('❌ Failed to add item: ' + error.message, 'error');
        })
        .finally(() => {
            // Reset button state
            if (button) {
                button.disabled = false;
                button.innerHTML = originalText;
            }
        });
}

function showNotification(message, type) {
    // Remove existing notification
    const existingNotification = document.getElementById('basket-notification');
    if (existingNotification) {
        existingNotification.remove();
    }

    // Create new notification using your message styles
    const notificationEl = document.createElement('div');
    notificationEl.id = 'basket-notification';
    notificationEl.className = `notification-message ${type}`;
    notificationEl.textContent = message;

    document.body.appendChild(notificationEl);

    // Auto-hide after 4 seconds
    setTimeout(() => {
        if (notificationEl.parentNode) {
            notificationEl.remove();
        }
    }, 4000);
}

function updateBasketCount(count) {
    console.log('Updating basket count to:', count);

    // Find the basket button in navbar
    const basketButton = document.querySelector('.basket-button');
    if (!basketButton) {
        console.log('Basket button not found in navbar');
        return;
    }

    // Look for existing basket count element
    let basketCountEl = basketButton.querySelector('.basket-count');

    // Create basket count element if it doesn't exist
    if (!basketCountEl) {
        basketCountEl = document.createElement('span');
        basketCountEl.className = 'basket-count';
        basketButton.style.position = 'relative';
        basketButton.appendChild(basketCountEl);
    }

    // Update the count
    basketCountEl.textContent = count;
    basketCountEl.style.display = count > 0 ? 'flex' : 'none';
}

// Load initial basket count when page loads
document.addEventListener('DOMContentLoaded', function() {
    console.log('Page loaded, fetching basket count...');

    fetch('/basket/count')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(count => {
            console.log('Initial basket count:', count);
            updateBasketCount(count);
        })
        .catch(error => {
            console.log('Could not load basket count:', error);
            // This is not critical, so we just log it
        });
});
