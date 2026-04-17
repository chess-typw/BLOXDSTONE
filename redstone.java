//------------------------------_FAKE------------------
//-------------------------BLOXDSTONE------------------
powerGrid = []
lastCheck = 0

isSource = (sx, sy, sz) => {
    let block = api.getBlock(sx, sy, sz)
    if (block === "Red Concrete" || block === "Red Torch") return true
    for (let i = 0; i < powerGrid.length; i++) {
        let p = powerGrid[i]
        if (p.x === sx && p.y === sy && p.z === sz) {
            if (p.type === "observer" || p.type === "lever" || p.type === "repeater" || p.type === "comparator") {
                return p.active
            }
        }
    }
    return false
}

findPowerSource = (startX, startY, startZ, isSilent) => {
    let directions = [[1, 0, 0], [-1, 0, 0], [0, 1, 0], [0, -1, 0], [0, 0, 1], [0, 0, -1]]
    for (let i = 0; i < directions.length; i++) {
        if (isSource(startX + directions[i][0], startY + directions[i][1], startZ + directions[i][2])) {
            if (!isSilent) api.log("log 5")
            return true
        }
    }
    let visited = []
    let queue = [{x: startX, y: startY, z: startZ}]
    while (queue.length > 0) {
        let curr = queue.shift()
        let posKey = curr.x + "," + curr.y + "," + curr.z
        if (visited.indexOf(posKey) !== -1) continue
        visited.push(posKey)
        for (let i = 0; i < directions.length; i++) {
            let nx = curr.x + directions[i][0], ny = curr.y + directions[i][1], nz = curr.z + directions[i][2]
            let bName = api.getBlock(nx, ny, nz)
            if (isSource(nx, ny, nz)) {
                if (!isSilent) api.log("log 7")
                return true
            }
            if (bName.includes("Red Concrete Slab")) {
                if (visited.indexOf(nx + "," + ny + "," + nz) === -1) {
                    queue.push({x: nx, y: ny, z: nz})
                }
            }
        }
    }
    return false
}

onPlayerAttemptAltAction = (playerId, x, y, z, block) => {
    if (block.includes("Crate")) {
        for (let i = 0; i < powerGrid.length; i++) {
            let p = powerGrid[i]
            if (p.x === x && p.y === y && p.z === z && p.type === "lever") {
                p.active = !p.active
                if (p.active) api.log("log 19"); else api.log("log 20")
                return "preventAction"
            }
        }
        powerGrid.push({x: x, y: y, z: z, type: "lever", active: true})
        api.log("log 19")
        return "preventAction"
    }
    if (block.includes("Green Planks")) {
        for (let i = 0; i < powerGrid.length; i++) {
            let p = powerGrid[i]
            if (p.x === x && p.y === y && p.z === z && p.type === "repeater") {
                if (p.delay === 1000) { p.delay = 2000; api.log("log 22") }
                else if (p.delay === 2000) { p.delay = 3000; api.log("log 23") }
                else if (p.delay === 3000) { p.delay = 4000; api.log("log 24") }
                else { p.delay = 1000; api.log("log 21") }
                return "preventAction"
            }
        }
    }
}

onPlayerChangeBlock = (playerId, x, y, z, fromBlock, toBlock) => {
    if (toBlock.includes("Red Concrete Slab")) {
        let conn = (api.getBlock(x+1, y, z).includes("Slab") || api.getBlock(x-1, y, z).includes("Slab") || api.getBlock(x, y+1, z).includes("Slab") || api.getBlock(x, y-1, z).includes("Slab") || api.getBlock(x, y, z+1).includes("Slab") || api.getBlock(x, y, z-1).includes("Slab"))
        if (conn) api.log("log 2"); else api.log("error 1")
    }
    if (toBlock === "Red Concrete" || toBlock === "Red Torch") api.log("log 10")
    if (toBlock.includes("Diorite")) { api.log("log 11"); powerGrid.push({x: x, y: y, z: z, type: "piston", extended: false, dir: null, sticky: false}) }
    if (toBlock.includes("Iron Watermelon")) { api.log("log 15"); powerGrid.push({x: x, y: y, z: z, type: "observer", dir: null, active: false}) }
    if (toBlock.includes("Green Planks")) { powerGrid.push({x: x, y: y, z: z, type: "repeater", active: false, delay: 1000, timer: 0, lastInput: false, dir: null}) }
    if (toBlock.includes("Yellow Ceramic")) { powerGrid.push({x: x, y: y, z: z, type: "comparator", active: false, dir: null}) }
    
    /* Place "Dim Lamp Off" to register it as a redstone lamp */
    if (toBlock === "Dim Lamp Off") {
        powerGrid.push({x: x, y: y, z: z, type: "light"})
    }
    
    let isHelper = toBlock.includes("Maple Wood Planks") || toBlock.includes("Stone") || toBlock.includes("Yellow Planks")
    if (isHelper) {
        let directions = [[1, 0, 0], [-1, 0, 0], [0, 1, 0], [0, -1, 0], [0, 0, 1], [0, 0, -1]]
        for (let i = 0; i < directions.length; i++) {
            let dx = x - directions[i][0], dy = y - directions[i][1], dz = z - directions[i][2]
            for (let j = 0; j < powerGrid.length; j++) {
                let p = powerGrid[j]
                if (p.x === dx && p.y === dy && p.z === dz && p.dir === null) {
                    p.dir = {x: directions[i][0], y: directions[i][1], z: directions[i][2]}
                    if (p.type === "piston") api.log("log 12")
                    if (p.type === "observer") api.log("log 16")
                    api.setBlock(x, y, z, "Air")
                    return
                }
            }
        }
    }
}

tick = () => {
    let now = api.now()
    if (now - lastCheck >= 200) {
        lastCheck = now
        let active = []
        for (let i = 0; i < powerGrid.length; i++) {
            let p = powerGrid[i]
            let block = api.getBlock(p.x, p.y, p.z)
            if (block.includes("Air")) continue
            active.push(p)

            if (p.type === "observer" && p.dir !== null) {
                let seen = api.getBlock(p.x + p.dir.x, p.y + p.dir.y, p.z + p.dir.z)
                if (!seen.includes("Air") && !p.active) api.log("log 18")
                p.active = (!seen.includes("Air"))
            }
            if (p.type === "repeater" && p.dir !== null) {
                let inputX = p.x - p.dir.x, inputY = p.y - p.dir.y, inputZ = p.z - p.dir.z
                let hasInput = findPowerSource(inputX, inputY, inputZ, true)
                if (hasInput !== p.lastInput) { p.timer = now + p.delay; p.lastInput = hasInput }
                if (now >= p.timer) p.active = p.lastInput
            }
            if (p.type === "comparator" && p.dir !== null) {
                let backX = p.x - p.dir.x, backY = p.y - p.dir.y, backZ = p.z - p.dir.z
                let backBlock = api.getBlock(backX, backY, backZ)
                if (backBlock.includes("Chest")) {
                    let items = api.getStandardChestItems([backX, backY, backZ])
                    let hasItems = false
                    if (items) {
                        for (let item of items) { if (item && item.name && !item.name.includes("Air")) { hasItems = true; break } }
                    }
                    if (hasItems && !p.active) api.log("log 25")
                    if (!hasItems && p.active) api.log("log 26")
                    p.active = hasItems
                } else { p.active = false }
            }
        }
        for (let i = 0; i < active.length; i++) {
            let p = active[i]
            let block = api.getBlock(p.x, p.y, p.z)
            let hasPower = findPowerSource(p.x, p.y, p.z, true)
            
            /* Lamp Logic: Swapping between On and Off */
            if (p.type === "light") {
                if (hasPower && block === "Dim Lamp Off") api.setBlock(p.x, p.y, p.z, "Dim Lamp On")
                if (!hasPower && block === "Dim Lamp On") api.setBlock(p.x, p.y, p.z, "Dim Lamp Off")
            }

            if (p.type === "piston" && p.dir !== null) {
                let headX = p.x + p.dir.x, headY = p.y + p.dir.y, headZ = p.z + p.dir.z
                let destX = headX + p.dir.x, destY = headY + p.dir.y, destZ = headZ + p.dir.z
                if (hasPower && !p.extended) {
                    let target = api.getBlock(headX, headY, headZ)
                    if (target.includes("Bedrock") || target.includes("Obsidian")) { api.log("error 14"); continue }
                    if (api.getBlock(destX, destY, destZ).includes("Air")) {
                        if (!target.includes("Air")) { api.setBlock(destX, destY, destZ, target); p.sticky = target.includes("Block of Moonstone") }
                        api.setBlock(headX, headY, headZ, "Iron Spikes"); p.extended = true
                    }
                } else if (!hasPower && p.extended) {
                    api.setBlock(headX, headY, headZ, "Air")
                    if (p.sticky && api.getBlock(destX, destY, destZ).includes("Block of Moonstone")) { api.setBlock(headX, headY, headZ, "Block of Moonstone"); api.setBlock(destX, destY, destZ, "Air") }
                    p.extended = false; p.sticky = false
                }
            }
        }
        powerGrid = active
    }
}


/* 
License: MIT
Copyright (c) 2026 [_FAKE]
Permission is hereby granted to anyone to use, copy, and modify this code.
*/
