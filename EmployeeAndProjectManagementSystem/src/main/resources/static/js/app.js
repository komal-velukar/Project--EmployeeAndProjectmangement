// src/main/resources/static/js/app.js

document.addEventListener('DOMContentLoaded', () => {
    const appContent = document.getElementById('app-content');
    
    // --- Router Configuration ---
    const routes = {
        // ... (all your route definitions) ...
    };

    function renderView(hash) {
        // ...
    }

    // --- Core View Rendering Functions (renderDashboard, renderEmployeesReport, etc.) ---
    
    function renderDashboard() {
        // ... uses the global API.getDashboardSummary() ...
    }

    // ... (All other rendering functions) ...
    
    // --- Event Handlers and Utility Functions ---
    
    function handleLogin(event) {
        // ...
    }
    
    function downloadPdf(invoiceId, invoiceNumber) {
        // ... uses the global API.getAuthHeader() ...
    }
    // EXPOSE THE UTILITY FUNCTION GLOBALLY FOR HTML ONCLICK ATTRIBUTES
    window.downloadPdf = downloadPdf; 


    // --- Initialization ---
    window.addEventListener('hashchange', () => renderView(window.location.hash));
    renderView(window.location.hash || '#dashboard');
});