const API_BASE_URL = '/api';

const API = {
    // --- AUTHENTICATION ---
    getAuthHeader: function() {
        const token = localStorage.getItem('epms_token');
        // This assumes you set up Spring Security to return a JWT or similar token
        // For Basic Auth (if using basic), you need the base64 encoded credentials
        return token ? `Bearer ${token}` : ''; 
    },

    // --- GENERIC FETCH FUNCTION ---
    fetchData: function(endpoint, method = 'GET', data = null) {
        return fetch(API_BASE_URL + endpoint, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': this.getAuthHeader()
            },
            body: data ? JSON.stringify(data) : null
        })
        .then(response => {
            if (response.status === 401 || response.status === 403) {
                // Redirect to login page if unauthorized
                window.location.hash = '#login';
                return Promise.reject('Unauthorized or Forbidden');
            }
            if (!response.ok) {
                return response.json().then(err => Promise.reject(err));
            }
            // Check if response has content before parsing JSON
            const contentType = response.headers.get("content-type");
            if (contentType && contentType.indexOf("application/json") !== -1) {
                return response.json();
            }
            return response.text();
        });
    },

    // --- APPLICATION SPECIFIC ENDPOINTS ---

    getDashboardSummary: function() {
        return this.fetchData('/dashboard/summary');
    },

    getEmployees: function() {
        return this.fetchData('/employees');
    },

    submitProject: function(proposalData) {
        return this.fetchData('/proposals', 'POST', proposalData);
    }
};