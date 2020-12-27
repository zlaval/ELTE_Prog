const placesInput = document.querySelector('#places')
const speciesInput = document.querySelector('#species')
const button = document.querySelector('#btn-generate')
const tableContainer = document.querySelector('#table-container')
const task1 = document.querySelector('#task-1')
const task2 = document.querySelector('#task-2')
const task3 = document.querySelector('#task-3')
const task4 = document.querySelector('#task-4')
const task5 = document.querySelector('#task-5')

let matrix = []

button.addEventListener('click', onGenerate)
function onGenerate(e) {
  const n = placesInput.valueAsNumber
  const m = speciesInput.valueAsNumber

  matrix = generateMatrix(n, m)
  console.log(matrix);
  renderTable()
  calculateQuestions()
}

function generateMatrix(n, m) {
  const matrix = []
  for (let i = 0; i < n; i++) {
    const row = []
    for (let j = 0; j < m; j++) {
      row.push(0)
    }
    matrix.push(row)
  }
  return matrix
}


function renderTable() {
  let tag = '<table class="bird-table">'
  for (let rows of matrix) {
    tag += "<tr>"
    for (let col of rows) {
      tag += `<td>${col}</td>`
    }
    tag += "</tr>"
  }
  tag += "</table>"
  tableContainer.innerHTML = tag
}

function tableColClick(event) {
  const targetCol = event.target
  const table = targetCol.parentElement.parentElement

  let i = 0
  let j = 0

  for (let row of table.rows) {
    for (let col of row.cells) {
      if (col === targetCol) {
        console.log(`Click: ${i + 1} row and ${j + 1} col`)
        matrix[i][j] = matrix[i][j] + 1
        renderTable()
        calculateQuestions()
      }
      j++
    }
    j = 0
    i++
  }
}

function calculateQuestions() {
  const hasAnyBird = matrix[0].some(e => e !== 0)
  if (hasAnyBird) {
    task1.innerHTML = "YES"
  } else {
    task1.innerHTML = "NO"
  }

  let moreThanTen = 0
  let placeIndex = -1
  let index = 1
  for (let place of matrix) {
    let more = false
    let hasBird = false
    for (let bird of place) {
      if (bird > 10) {
        more = true
      }
      if (bird > 0) {
        hasBird = true
      }
    }
    if (!hasBird) {
      placeIndex = index
    }
    if (more) {
      moreThanTen++
    }
    index++
  }

  task2.innerHTML = moreThanTen
  if (placeIndex === -1) {
    task3.innerHTML = "NO"
  } else {
    task3.innerHTML = placeIndex
  }
}


delegate(tableContainer, "click", "td", null, tableColClick)


function delegate(parent, type, selector, eventSource, handler) {
  parent.addEventListener(type, function (event) {
    const eventElement = event.target.closest(eventSource);
    const targetElement = event.target.closest(selector);
    if (!eventSource || eventElement === event.target) {
      if (this.contains(targetElement)) {
        handler.call(targetElement, event);
      }
    }
  })
}
