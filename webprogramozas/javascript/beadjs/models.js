export class Options {
    playerCount = 1
    additionalCard = AdditionalCardMode.AUTO
    gameMode = GameMode.PRACTICE
    findSetBtn = false
    showSetBtn = false
    advanced = false
}

export const AdditionalCardMode = {
    AUTO: 'automatic',
    BTN: 'button'
}

export const GameMode = {
    PRACTICE: 'practice',
    COMPETITION: 'competition'
}

export const Color = {
    RED: 'r',
    GREEN: 'g',
    PURPLE: 'p'
}

export const Form = {
    OVAL: 'P',
    WAVY: 'S',
    DIAMOND: 'D'
}

export const Number = {
    ONE: 1,
    TWO: 2,
    THREE: 3
}

export const Content = {
    FULL: 'S',
    EMPTY: 'O',
    STRIP: 'H'

}

export class Player {
    constructor(id, name) {
        this.id = id
        this.name = name
        this.point = 0
        this.active = true
        this.sumPoints = null
    }
}

export class Card {
    constructor(number, color, form, content) {
        this.number = number
        this.color = color
        this.form = form
        this.content = content
        this.cardId = null
    }
}