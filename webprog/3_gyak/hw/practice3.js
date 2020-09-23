const taskInput = document.querySelector('#task-input');
const taskList = document.querySelector('#tasks');
const removeAllButton = document.querySelector('#remove-all-button');
const removeFinishedBtn = document.querySelector('#remove-finished-button')
const tasksArray = [];

taskInput.parentElement.addEventListener('submit', function (event) {
    event.preventDefault();

    const tasks = taskInput.value.split(',');

    for (const task of tasks) {
        if (task && !tasksArray.includes(task)) {
            taskList.innerHTML += `<li data-index="${tasksArray.length}">${task} <button type="button">x</button></li>`;
            tasksArray.push(task);
            taskInput.value = '';
        }
    }
});

removeAllButton.addEventListener('click', function () {
    taskList.innerHTML = '';
    tasksArray.length = 0;
});

taskList.addEventListener('click', function (event) {
    event.stopPropagation();

    if (event.target.tagName === 'LI') {
        event.target.classList.toggle('done');
    }
    else if (event.target.matches('button')) {
        tasksArray.splice(+event.target.parentElement.dataset.index, 1);
        taskList.removeChild(event.target.parentElement);
    }
});

removeFinishedBtn.addEventListener('click', function () {
    document.querySelectorAll('.done').forEach(e => {
        const index=e.getAttribute('data-index')
        tasksArray.splice(index,1)
        e.remove()
    })
})
