document.addEventListener('DOMContentLoaded', () => {
    fetchRuns();

    document.getElementById('addRunForm').addEventListener('submit', handleFormSubmit);
});

async function fetchRuns() {
    try {
        const response = await fetch('/api/runs');
        const runs = await response.json();

        const runList = document.getElementById('runList');
        runList.innerHTML = '';

        runs.forEach(run => {
            const runDiv = document.createElement('div');
            runDiv.classList.add('run');
            runDiv.innerHTML = `
                <h3>${run.title}</h3>
                <p>ID: ${run.id}</p>
                <p>Started On: ${new Date(run.startedOn).toLocaleString()}</p>
                <p>Completed On: ${new Date(run.completedOn).toLocaleString()}</p>
                <p>Miles: ${run.miles}</p>
                <p>Location: ${run.location}</p>
                <button onclick="deleteRun(${run.id})">Delete</button>
            `;
            runList.appendChild(runDiv);

        });
    } catch (error) {
        console.error('Error fetching runs:', error);
    }
}

async function handleFormSubmit(event) {
    event.preventDefault();
    const run = {
        title: document.getElementById('title').value,
        startedOn: document.getElementById('startedOn').value,
        completedOn: document.getElementById('completedOn').value,
        miles: document.getElementById('miles').value,
        location: document.getElementById('location').value
    };

    try {
        const response = await fetch('/api/runs', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(run)
        });

        if (response.ok) {
            await fetchRuns();
            clearForm();

        } else {
            console.error('Failed to add run:', response.statusText);
        }
    } catch (error) {
        console.error('Error adding run:', error);
    }
}

function clearForm() {
    //document.getElementById('runId').value = '';
    document.getElementById('addRunForm').reset();
}

async function deleteRun(id) {
    try {
        const response = await fetch(`/api/runs/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            await fetchRuns();
        } else {
            console.error('Failed to delete run:', response.statusText);
        }
    } catch (error) {
        console.error('Error deleting run:', error);
    }
}
