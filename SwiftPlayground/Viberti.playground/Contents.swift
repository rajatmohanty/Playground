enum State: String, CaseIterable {
    case healthy
    case fever
}

enum Observation: CaseIterable {
    case normal
    case cold
    case dizzy
}

typealias InitProbability = [State: Double]

let initProbability: InitProbability =
    [
        .healthy: 0.6, .fever: 0.4
    ]

typealias TransitionProbability = [State: [State: Double]]
let transitionProbability: TransitionProbability =
    [
        .healthy: [.healthy: 0.7, .fever: 0.3],
        .fever: [.healthy: 0.4, .fever: 0.6]
    ]

typealias EmissionProbability = [State: [Observation: Double]]
let emissionProbability: [State: [Observation: Double]] =
    [
        .healthy: [.normal: 0.5, .cold: 0.4, .dizzy: 0.1],
        .fever: [.normal: 0.1, .cold: 0.3, .dizzy: 0.6]
    ]

func viberti(observationSequence: [Observation],
             states: [State],
             initProbability: InitProbability,
             transitionProbability: TransitionProbability,
             emissionProbability: EmissionProbability) -> [State] {
    var path: [State: [State]] = [:]
    var currentProbability: InitProbability = [:]
    for state in states {
        path[state] = []
        currentProbability[state] = initProbability[state]! * emissionProbability[state]![observationSequence[0]]!
    }
    var lastProbability: InitProbability = [:]
    var maxProbability: Double = -1.0
    var maxState: State = .healthy
    for i in 1..<observationSequence.count {
        lastProbability = currentProbability
        currentProbability = [:]
        for currentState in states {
            maxProbability = -1.0
            for lastState in states {
                if let lastProbability = lastProbability[lastState],
                    let lastTransition = transitionProbability[lastState],
                    let transitionProbability = lastTransition[currentState],
                    let currentEmission = emissionProbability[currentState],
                    let emissionProbability = currentEmission[observationSequence[i]] {
                    let newProbability = lastProbability * transitionProbability * emissionProbability
                    if newProbability > maxProbability {
                        maxProbability = newProbability
                        maxState = lastState
                    }
                }
            }
            
            currentProbability[currentState] = maxProbability
            path[currentState]!.append(maxState)
        }
    }
    
    maxProbability = -1.0
    var maxPath: [State] = []
    for state in states {
        var pathArray: [State] = path[state]!
        pathArray.append(state)
        if currentProbability[state]! > maxProbability {
            maxPath = pathArray
            maxProbability = currentProbability[state]!
        }
    }
    return maxPath
}

print(viberti(observationSequence: [.normal, .cold, .dizzy],
              states: State.allCases,
              initProbability: initProbability,
              transitionProbability: transitionProbability,
              emissionProbability: emissionProbability).map { $0.rawValue })
