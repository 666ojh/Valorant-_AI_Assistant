const TOKEN_STORAGE_KEY = "valorant.assistant.token";
const API_BASE_STORAGE_KEY = "valorant.assistant.apiBase";
const BLANK_IMAGE = "data:image/gif;base64,R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs=";
const DEFAULT_LOCAL_API_BASE = "http://127.0.0.1:8080";

const previewMatches = [
    {
        playerId: 1,
        matchId: 101,
        matchCode: "CN-SPLIT-101",
        mapName: "Split",
        modeCode: "COMPETITIVE",
        startedAt: "2026-06-27T20:12:00",
        endedAt: "2026-06-27T20:54:00",
        durationSeconds: 2520,
        redScore: 13,
        blueScore: 9,
        winningTeam: "RED",
        teamCode: "RED",
        agentCode: "Jett",
        won: true,
        kills: 24,
        deaths: 15,
        assists: 6,
        averageCombatScore: 287,
        damageDealt: 4280,
        damageReceived: 2650,
        headshotRate: 28.4,
        kastRate: 74.8,
        firstKills: 5,
        firstDeaths: 2,
        plants: 1,
        defuses: 0,
        rankTier: "Diamond 2",
        heatmap: [34, 52, 68, 74, 36, 28, 48, 86, 92, 53, 18, 42, 72, 80, 44, 10, 30, 58, 64, 38, 8, 18, 28, 34, 20],
        roundFlow: ["W", "W", "L", "W", "W", "W", "L", "L", "W", "W", "W", "L"],
        loadout: [
            { name: "Rifle", value: 64 },
            { name: "Sniper", value: 18 },
            { name: "Pistol", value: 11 },
            { name: "Skill", value: 7 }
        ]
    },
    {
        playerId: 1,
        matchId: 102,
        matchCode: "CN-ASCENT-102",
        mapName: "Ascent",
        modeCode: "COMPETITIVE",
        startedAt: "2026-06-27T18:40:00",
        endedAt: "2026-06-27T19:26:00",
        durationSeconds: 2760,
        redScore: 10,
        blueScore: 13,
        winningTeam: "BLUE",
        teamCode: "RED",
        agentCode: "Omen",
        won: false,
        kills: 17,
        deaths: 18,
        assists: 11,
        averageCombatScore: 221,
        damageDealt: 3325,
        damageReceived: 3180,
        headshotRate: 19.6,
        kastRate: 69.1,
        firstKills: 2,
        firstDeaths: 4,
        plants: 2,
        defuses: 1,
        rankTier: "Diamond 2",
        heatmap: [18, 22, 34, 48, 32, 30, 40, 60, 74, 54, 22, 36, 58, 78, 62, 16, 28, 44, 55, 46, 10, 18, 26, 35, 24],
        roundFlow: ["L", "W", "W", "L", "L", "W", "L", "W", "L", "L", "W", "L"],
        loadout: [
            { name: "Rifle", value: 56 },
            { name: "Sniper", value: 8 },
            { name: "Pistol", value: 20 },
            { name: "Skill", value: 16 }
        ]
    },
    {
        playerId: 1,
        matchId: 103,
        matchCode: "CN-LOTUS-103",
        mapName: "Lotus",
        modeCode: "COMPETITIVE",
        startedAt: "2026-06-26T22:10:00",
        endedAt: "2026-06-26T22:47:00",
        durationSeconds: 2220,
        redScore: 13,
        blueScore: 5,
        winningTeam: "RED",
        teamCode: "RED",
        agentCode: "Raze",
        won: true,
        kills: 21,
        deaths: 11,
        assists: 8,
        averageCombatScore: 304,
        damageDealt: 3960,
        damageReceived: 2190,
        headshotRate: 24.1,
        kastRate: 78.6,
        firstKills: 4,
        firstDeaths: 1,
        plants: 0,
        defuses: 0,
        rankTier: "Diamond 2",
        heatmap: [26, 46, 74, 88, 40, 22, 50, 84, 96, 48, 16, 42, 70, 82, 44, 12, 28, 54, 66, 36, 6, 16, 24, 32, 18],
        roundFlow: ["W", "W", "W", "L", "W", "W", "W", "W", "L", "W", "W", "W"],
        loadout: [
            { name: "Rifle", value: 60 },
            { name: "Sniper", value: 6 },
            { name: "Pistol", value: 14 },
            { name: "Skill", value: 20 }
        ]
    },
    {
        playerId: 1,
        matchId: 104,
        matchCode: "CN-BIND-104",
        mapName: "Bind",
        modeCode: "COMPETITIVE",
        startedAt: "2026-06-26T20:05:00",
        endedAt: "2026-06-26T20:50:00",
        durationSeconds: 2700,
        redScore: 11,
        blueScore: 13,
        winningTeam: "BLUE",
        teamCode: "RED",
        agentCode: "Cypher",
        won: false,
        kills: 14,
        deaths: 17,
        assists: 10,
        averageCombatScore: 198,
        damageDealt: 2910,
        damageReceived: 3090,
        headshotRate: 31.2,
        kastRate: 66.5,
        firstKills: 1,
        firstDeaths: 3,
        plants: 1,
        defuses: 2,
        rankTier: "Diamond 1",
        heatmap: [12, 20, 32, 38, 26, 20, 34, 48, 52, 36, 26, 44, 62, 68, 40, 18, 28, 42, 46, 30, 10, 16, 20, 24, 14],
        roundFlow: ["L", "L", "W", "L", "W", "L", "W", "W", "L", "L", "W", "L"],
        loadout: [
            { name: "Rifle", value: 51 },
            { name: "Sniper", value: 4 },
            { name: "Pistol", value: 18 },
            { name: "Skill", value: 27 }
        ]
    }
];

const state = {
    matches: [],
    selectedMatchId: null,
    source: "LOCKED",
    token: localStorage.getItem(TOKEN_STORAGE_KEY),
    session: null,
    dashboardAssets: createEmptyAssets(),
    authMessage: "After login, the page will load the current account and its primary player automatically.",
    isLoading: false
};

const queryParams = new URLSearchParams(window.location.search);

const elements = {
    brandLogo: document.getElementById("brandLogo"),
    brandFallback: document.getElementById("brandFallback"),
    loggedInPanel: document.getElementById("loggedInPanel"),
    loginForm: document.getElementById("loginForm"),
    usernameInput: document.getElementById("usernameInput"),
    passwordInput: document.getElementById("passwordInput"),
    loginButton: document.getElementById("loginButton"),
    refreshButton: document.getElementById("refreshButton"),
    logoutButton: document.getElementById("logoutButton"),
    userAvatarInput: document.getElementById("userAvatarInput"),
    authAvatar: document.getElementById("authAvatar"),
    authDisplayName: document.getElementById("authDisplayName"),
    authSubtitle: document.getElementById("authSubtitle"),
    roleBadge: document.getElementById("roleBadge"),
    authMessage: document.getElementById("authMessage"),
    dataSourceBadge: document.getElementById("dataSourceBadge"),
    syncHint: document.getElementById("syncHint"),
    playerCardPanel: document.getElementById("playerCardPanel"),
    playerAvatar: document.getElementById("playerAvatar"),
    playerName: document.getElementById("playerName"),
    playerMeta: document.getElementById("playerMeta"),
    playerRank: document.getElementById("playerRank"),
    playerPlatform: document.getElementById("playerPlatform"),
    playerLevel: document.getElementById("playerLevel"),
    playerRegion: document.getElementById("playerRegion"),
    heroPanel: document.getElementById("heroPanel"),
    engagementPanel: document.getElementById("engagementPanel"),
    historyPanel: document.getElementById("historyPanel"),
    heatmapPanel: document.getElementById("heatmapPanel"),
    timelinePanel: document.getElementById("timelinePanel"),
    loadoutPanel: document.getElementById("loadoutPanel"),
    heroPoster: document.getElementById("heroPoster"),
    heroTitle: document.getElementById("heroTitle"),
    heroMap: document.getElementById("heroMap"),
    heroMode: document.getElementById("heroMode"),
    heroResult: document.getElementById("heroResult"),
    sessionStatus: document.getElementById("sessionStatus"),
    agentName: document.getElementById("agentName"),
    resultBadge: document.getElementById("resultBadge"),
    scoreline: document.getElementById("scoreline"),
    kdaLine: document.getElementById("kdaLine"),
    acsValue: document.getElementById("acsValue"),
    headshotValue: document.getElementById("headshotValue"),
    kastValue: document.getElementById("kastValue"),
    durationValue: document.getElementById("durationValue"),
    engagementMetrics: document.getElementById("engagementMetrics"),
    matchList: document.getElementById("matchList"),
    heatmapGrid: document.getElementById("heatmapGrid"),
    roundFlow: document.getElementById("roundFlow"),
    timelineNote: document.getElementById("timelineNote"),
    loadoutBars: document.getElementById("loadoutBars")
};

function createEmptyAssets() {
    return {
        enabled: false,
        ossBaseUrl: null,
        logoUrl: null,
        defaultAvatarUrl: null,
        sideAgentUrl: null,
        mapImages: {},
        agentImages: {}
    };
}

function normalizeBaseUrl(value) {
    if (!value) {
        return "";
    }
    return value.trim().replace(/\/+$/, "");
}

function resolveConfiguredApiBase() {
    const queryBase = normalizeBaseUrl(queryParams.get("apiBase"));
    if (queryBase) {
        localStorage.setItem(API_BASE_STORAGE_KEY, queryBase);
        return queryBase;
    }

    const storedBase = normalizeBaseUrl(localStorage.getItem(API_BASE_STORAGE_KEY));
    if (storedBase) {
        return storedBase;
    }

    if (window.location.protocol === "file:") {
        return DEFAULT_LOCAL_API_BASE;
    }

    return "";
}

function shouldFallbackToLocalApi() {
    return window.location.protocol === "file:"
        || (window.location.protocol.startsWith("http") && window.location.port !== "8080");
}

function buildApiUrl(path, baseUrl = resolveConfiguredApiBase()) {
    if (!baseUrl) {
        return path;
    }
    const normalizedPath = path.startsWith("/") ? path : `/${path}`;
    return `${normalizeBaseUrl(baseUrl)}${normalizedPath}`;
}

function describeApiBase(baseUrl = resolveConfiguredApiBase()) {
    if (baseUrl) {
        return normalizeBaseUrl(baseUrl);
    }
    return window.location.origin || DEFAULT_LOCAL_API_BASE;
}

function setStoredToken(token) {
    state.token = token;
    if (token) {
        localStorage.setItem(TOKEN_STORAGE_KEY, token);
        return;
    }
    localStorage.removeItem(TOKEN_STORAGE_KEY);
}

function formatPercent(value) {
    return `${Number(value || 0).toFixed(1)}%`;
}

function formatDuration(seconds) {
    const minutes = Math.max(0, Math.round((seconds || 0) / 60));
    return `${minutes}m`;
}

function formatDateTime(input) {
    const date = new Date(input);
    return `${date.getMonth() + 1}/${date.getDate()} ${String(date.getHours()).padStart(2, "0")}:${String(date.getMinutes()).padStart(2, "0")}`;
}

function translateMode(modeCode) {
    const mapping = {
        COMPETITIVE: "Competitive",
        UNRATED: "Unrated",
        SWIFTPLAY: "Swiftplay"
    };
    return mapping[modeCode] || modeCode || "-";
}

function translateResult(match) {
    if (!match) {
        return "-";
    }
    return match.won ? "Win" : "Loss";
}

function buildFallbackHeatmap(seed) {
    return Array.from({ length: 25 }, (_, index) => {
        const wave = (seed * 7 + index * 13) % 100;
        return Math.max(8, wave);
    });
}

function buildFallbackRoundFlow(seed, won) {
    return Array.from({ length: 12 }, (_, index) => {
        const toggle = (seed + index * 3) % 5;
        if (won) {
            return toggle === 0 ? "L" : "W";
        }
        return toggle <= 1 ? "W" : "L";
    });
}

function buildFallbackLoadout(seed) {
    const rifle = 48 + (seed % 18);
    const sniper = 4 + (seed % 12);
    const pistol = 12 + (seed % 10);
    const skill = 100 - rifle - sniper - pistol;
    return [
        { name: "Rifle", value: rifle },
        { name: "Sniper", value: sniper },
        { name: "Pistol", value: pistol },
        { name: "Skill", value: skill }
    ];
}

function getMapImageUrl(mapName) {
    return state.dashboardAssets.mapImages?.[mapName] || null;
}

function getAgentImageUrl(agentCode) {
    return state.dashboardAssets.agentImages?.[agentCode] || null;
}

function getDefaultAvatarUrl() {
    return state.dashboardAssets.defaultAvatarUrl || "";
}

function setImageSource(element, url) {
    element.src = url || BLANK_IMAGE;
}

function setLockedState(isLocked) {
    [
        elements.playerCardPanel,
        elements.heroPanel,
        elements.engagementPanel,
        elements.historyPanel,
        elements.heatmapPanel,
        elements.timelinePanel,
        elements.loadoutPanel
    ].forEach((element) => {
        element.classList.toggle("is-locked", isLocked);
    });
}

function normalizeDetail(detail) {
    const seed = Number(detail.matchId || 1);
    return {
        ...detail,
        heatmap: detail.heatmap || buildFallbackHeatmap(seed),
        roundFlow: detail.roundFlow || buildFallbackRoundFlow(seed, detail.won),
        loadout: detail.loadout || buildFallbackLoadout(seed),
        mapImageUrl: detail.mapImageUrl || getMapImageUrl(detail.mapName),
        agentImageUrl: detail.agentImageUrl || getAgentImageUrl(detail.agentCode)
    };
}

function setSource(source, hint) {
    state.source = source;
    elements.dataSourceBadge.textContent = source;
    elements.syncHint.textContent = hint;
}

function setMatches(matches, source, hint) {
    state.matches = matches.map(normalizeDetail);
    state.selectedMatchId = state.matches[0]?.matchId || null;
    setSource(source, hint);
    render();
}

function getSelectedMatch() {
    return state.matches.find((item) => item.matchId === state.selectedMatchId) || state.matches[0] || null;
}

function applyBrandAsset() {
    if (state.dashboardAssets.logoUrl) {
        elements.brandLogo.src = state.dashboardAssets.logoUrl;
        elements.brandLogo.classList.remove("is-hidden");
        elements.brandFallback.classList.add("is-hidden");
        return;
    }
    elements.brandLogo.classList.add("is-hidden");
    elements.brandFallback.classList.remove("is-hidden");
}

function renderAuthPanel() {
    const user = state.session;
    if (!user) {
        elements.loggedInPanel.classList.add("is-hidden");
        elements.loginForm.classList.remove("is-hidden");
        elements.authMessage.textContent = state.authMessage;
        elements.sessionStatus.textContent = state.isLoading ? "Checking" : "Logged Out";
        return;
    }

    const currentPlayer = user.primaryPlayer;
    const avatarUrl = user.avatarUrl || currentPlayer?.avatarUrl || getDefaultAvatarUrl();
    elements.loggedInPanel.classList.remove("is-hidden");
    elements.loginForm.classList.add("is-hidden");
    setImageSource(elements.authAvatar, avatarUrl);
    elements.authDisplayName.textContent = user.displayName || user.username;
    elements.authSubtitle.textContent = currentPlayer
        ? `${currentPlayer.fullName || currentPlayer.gameName} - ${currentPlayer.rankTier || "Unranked"}`
        : "No primary player is bound to this account yet.";
    elements.roleBadge.textContent = user.roleCode || "USER";
    elements.authMessage.textContent = state.authMessage;
    elements.sessionStatus.textContent = currentPlayer ? "Live" : "No Player";
}

function renderPlayerCard() {
    const currentPlayer = state.session?.primaryPlayer;
    if (!currentPlayer) {
        setImageSource(elements.playerAvatar, getDefaultAvatarUrl());
        elements.playerName.textContent = "Waiting For Login";
        elements.playerMeta.textContent = "The dashboard will resolve the current account's primary player.";
        elements.playerRank.textContent = "-";
        elements.playerPlatform.textContent = "-";
        elements.playerLevel.textContent = "-";
        elements.playerRegion.textContent = "-";
        return;
    }

    setImageSource(elements.playerAvatar, currentPlayer.avatarUrl || getDefaultAvatarUrl());
    elements.playerName.textContent = currentPlayer.fullName || currentPlayer.gameName || "Unnamed Player";
    elements.playerMeta.textContent = `Player ID #${currentPlayer.playerId} - ${currentPlayer.status || "ACTIVE"}`;
    elements.playerRank.textContent = currentPlayer.rankTier || "-";
    elements.playerPlatform.textContent = currentPlayer.platform || "-";
    elements.playerLevel.textContent = currentPlayer.accountLevel ?? "-";
    elements.playerRegion.textContent = currentPlayer.regionCode || "-";
}

function renderMetrics(match) {
    const duelRate = Math.min(100, Math.round((match.kills / Math.max(1, match.deaths)) * 42));
    const entryRate = Math.min(100, match.firstKills * 12 + 22);
    const surviveRate = Math.min(100, Math.round(100 - (match.damageReceived / Math.max(1, match.damageDealt)) * 35));
    const metrics = [
        { label: "Duel Pressure", value: `${match.kills}/${match.deaths}`, progress: duelRate },
        { label: "Entry Impact", value: `${match.firstKills} picks`, progress: entryRate },
        { label: "Survival Rate", value: `${surviveRate}%`, progress: surviveRate },
        { label: "Team Sync", value: formatPercent(match.kastRate), progress: Number(match.kastRate || 0) }
    ];

    elements.engagementMetrics.innerHTML = metrics.map((metric) => `
        <div class="metric-row">
            <div class="metric-row__head">
                <span>${metric.label}</span>
                <strong>${metric.value}</strong>
            </div>
            <div class="metric-bar"><i style="width:${metric.progress}%"></i></div>
        </div>
    `).join("");
}

function renderMatchList() {
    elements.matchList.innerHTML = state.matches.map((match) => `
        <button class="match-card ${match.matchId === state.selectedMatchId ? "is-active" : ""}" type="button" data-match-id="${match.matchId}">
            <div class="match-card__thumb" style="${match.mapImageUrl ? `background-image: linear-gradient(145deg, rgba(255,70,85,0.16), rgba(20,216,207,0.18)), url('${match.mapImageUrl}')` : ""}"></div>
            <div class="match-card__body">
                <div class="match-card__top">
                    <div>
                        <strong>${match.mapName}</strong>
                        <div class="match-card__subtitle">${match.agentCode} - ${translateMode(match.modeCode)}</div>
                    </div>
                    <span class="match-pill ${match.won ? "is-win" : "is-loss"}">${translateResult(match)}</span>
                </div>
                <div class="match-card__bottom">
                    <span>${formatDateTime(match.startedAt)}</span>
                    <strong>${match.kills}/${match.deaths}/${match.assists}</strong>
                </div>
            </div>
        </button>
    `).join("");

    elements.matchList.querySelectorAll("[data-match-id]").forEach((button) => {
        button.addEventListener("click", () => {
            state.selectedMatchId = Number(button.dataset.matchId);
            render();
        });
    });
}

function renderHeatmap(match) {
    elements.heatmapGrid.innerHTML = match.heatmap.map((value, index) => {
        const opacity = Math.max(0.16, value / 100);
        const hue = value > 72 ? "var(--accent-cyan)" : "var(--accent-red)";
        return `
            <div class="heatmap-cell" style="background: linear-gradient(145deg, rgba(255,255,255,0.06), color-mix(in srgb, ${hue} ${Math.round(opacity * 100)}%, transparent)); animation-delay:${index * 35}ms">
                <span>${value}</span>
            </div>
        `;
    }).join("");
}

function renderRoundFlow(match) {
    elements.roundFlow.innerHTML = match.roundFlow.map((result, index) => `
        <div class="round-cell ${result === "W" ? "is-win" : "is-loss"}" style="animation-delay:${index * 40}ms">
            R${index + 1}
        </div>
    `).join("");

    const wins = match.roundFlow.filter((item) => item === "W").length;
    elements.timelineNote.textContent = `First 12 rounds: ${wins} wins. ${match.won ? "Momentum stayed high in late rounds." : "Late-round conversion dropped."}`;
}

function renderLoadout(match) {
    elements.loadoutBars.innerHTML = match.loadout.map((item) => `
        <div class="loadout-row">
            <span>${item.name}</span>
            <div class="loadout-track"><i style="width:${item.value}%"></i></div>
            <strong>${item.value}%</strong>
        </div>
    `).join("");
}

function renderHero(match) {
    const currentPlayer = state.session?.primaryPlayer;
    elements.heroTitle.textContent = currentPlayer
        ? `${currentPlayer.fullName || currentPlayer.gameName} - ${currentPlayer.rankTier || "Unranked"}`
        : `Preview Player - ${match.rankTier || "Preview"}`;
    elements.heroMap.textContent = match.mapName;
    elements.heroMode.textContent = translateMode(match.modeCode);
    elements.heroResult.textContent = translateResult(match);
    elements.agentName.textContent = match.agentCode;
    elements.resultBadge.textContent = translateResult(match);
    elements.resultBadge.className = `result-badge ${state.source === "PREVIEW" ? "is-preview" : match.won ? "is-win" : "is-loss"}`;
    elements.scoreline.textContent = `${match.redScore} : ${match.blueScore}`;
    elements.kdaLine.textContent = `${match.kills} / ${match.deaths} / ${match.assists}`;
    elements.acsValue.textContent = match.averageCombatScore;
    elements.headshotValue.textContent = formatPercent(match.headshotRate);
    elements.kastValue.textContent = formatPercent(match.kastRate);
    elements.durationValue.textContent = formatDuration(match.durationSeconds);

    const posterUrl = match.agentImageUrl || state.dashboardAssets.sideAgentUrl || match.mapImageUrl;
    if (posterUrl) {
        setImageSource(elements.heroPoster, posterUrl);
        elements.heroPoster.classList.remove("is-hidden");
    } else {
        elements.heroPoster.classList.add("is-hidden");
    }
}

function renderEmpty(message) {
    elements.heroTitle.textContent = message;
    elements.heroMap.textContent = "-";
    elements.heroMode.textContent = "-";
    elements.heroResult.textContent = "-";
    elements.agentName.textContent = "No Match";
    elements.resultBadge.textContent = state.session ? "Empty" : "Locked";
    elements.resultBadge.className = `result-badge ${state.session ? "" : "is-preview"}`;
    elements.scoreline.textContent = "0 : 0";
    elements.kdaLine.textContent = "0 / 0 / 0";
    elements.acsValue.textContent = "0";
    elements.headshotValue.textContent = "0%";
    elements.kastValue.textContent = "0%";
    elements.durationValue.textContent = "0m";
    elements.heroPoster.classList.add("is-hidden");
    elements.engagementMetrics.innerHTML = "";
    elements.matchList.innerHTML = `<div class="match-card"><div class="match-card__body"><strong>${state.session ? "No match data" : "Log in to view"}</strong><div class="match-card__subtitle">${message}</div></div></div>`;
    elements.heatmapGrid.innerHTML = "";
    elements.roundFlow.innerHTML = "";
    elements.timelineNote.textContent = "No round data.";
    elements.loadoutBars.innerHTML = "";
}

function render() {
    setLockedState(!state.session);
    renderAuthPanel();
    renderPlayerCard();
    applyBrandAsset();

    const selectedMatch = getSelectedMatch();
    if (!selectedMatch) {
        renderEmpty(state.session ? "The current primary player has no match data yet." : "Login to load the current primary player's matches.");
        return;
    }

    renderHero(selectedMatch);
    renderMetrics(selectedMatch);
    renderMatchList();
    renderHeatmap(selectedMatch);
    renderRoundFlow(selectedMatch);
    renderLoadout(selectedMatch);
}

async function fetchApi(url, options = {}) {
    const headers = {
        Accept: "application/json",
        ...(!options.formData && options.body ? { "Content-Type": "application/json" } : {}),
        ...(options.headers || {})
    };

    if (options.auth && state.token) {
        headers.Authorization = `Bearer ${state.token}`;
    }

    const executeRequest = async (baseUrl = resolveConfiguredApiBase()) => {
        const response = await fetch(buildApiUrl(url, baseUrl), {
            method: options.method || "GET",
            headers,
            body: options.formData || (options.body ? JSON.stringify(options.body) : undefined)
        });

        const payload = await response.json().catch(() => null);
        if (!response.ok || payload?.success === false) {
            const message = payload?.message || `HTTP ${response.status}`;
            throw new Error(message);
        }
        return payload?.data;
    };

    try {
        return await executeRequest();
    } catch (error) {
        const canRetryAgainstLocalApi = error instanceof TypeError
            && shouldFallbackToLocalApi()
            && normalizeBaseUrl(resolveConfiguredApiBase()) !== DEFAULT_LOCAL_API_BASE;

        if (canRetryAgainstLocalApi) {
            localStorage.setItem(API_BASE_STORAGE_KEY, DEFAULT_LOCAL_API_BASE);
            try {
                return await executeRequest(DEFAULT_LOCAL_API_BASE);
            } catch (retryError) {
                if (retryError instanceof TypeError) {
                    throw new Error(`Cannot reach backend API at ${DEFAULT_LOCAL_API_BASE}. Open the page from http://localhost:8080/valorant-dashboard/ or ensure the backend is running.`);
                }
                throw retryError;
            }
        }

        if (error instanceof TypeError) {
            throw new Error(`Cannot reach backend API at ${describeApiBase()}. Open the page from http://localhost:8080/valorant-dashboard/ or ensure the backend is running.`);
        }

        throw error;
    }
}

async function fetchMatchDetails(playerId) {
    const history = await fetchApi(`/api/v1/players/${playerId}/matches?page=0&size=6`, { auth: true });
    const records = history?.records || [];
    if (!records.length) {
        return [];
    }

    const details = await Promise.all(records.map((record) => {
        return fetchApi(`/api/v1/players/${playerId}/matches/${record.matchId}`, { auth: true });
    }));

    return details.filter(Boolean);
}

async function loadCurrentPlayerMatches() {
    const playerId = state.session?.primaryPlayer?.playerId;
    if (!playerId) {
        setMatches([], "API", "The account is logged in, but no primary player is bound yet.");
        return;
    }

    const details = await fetchMatchDetails(playerId);
    if (!details.length) {
        setMatches([], "API", "The current primary player has no match data yet.");
        return;
    }
    setMatches(details, "API", "Live match data loaded for the current account's primary player.");
}

async function bootstrapSession() {
    if (!state.token) {
        state.session = null;
        state.dashboardAssets = createEmptyAssets();
        state.authMessage = shouldFallbackToLocalApi()
            ? `Current page is using backend API ${describeApiBase()}. Log in to view live player data.`
            : "Log in to view live player data.";
        setMatches([], "LOCKED", "Log in to view live player data.");
        return;
    }

    state.isLoading = true;
    renderAuthPanel();

    try {
        const sessionContext = await fetchApi("/api/v1/auth/session-context", { auth: true });
        state.session = sessionContext;
        state.dashboardAssets = sessionContext.dashboardAssets || createEmptyAssets();
        state.authMessage = sessionContext.primaryPlayer
            ? "Session ready. Loading live stats for the current primary player."
            : "Session ready, but this account does not have a primary player yet.";
        await loadCurrentPlayerMatches();
    } catch (error) {
        console.warn("session bootstrap failed", error);
        setStoredToken(null);
        state.session = null;
        state.dashboardAssets = createEmptyAssets();
        state.authMessage = "Session expired. Please login again.";
        setMatches([], "LOCKED", "Session expired. Log in again to view player data.");
    } finally {
        state.isLoading = false;
        render();
    }
}

async function handleLogin(event) {
    event.preventDefault();
    const usernameOrEmail = elements.usernameInput.value.trim();
    const password = elements.passwordInput.value;
    if (!usernameOrEmail || !password) {
        state.authMessage = "Please enter both account and password.";
        renderAuthPanel();
        return;
    }

    elements.loginButton.disabled = true;
    state.authMessage = "Signing in and loading the current player's stats...";
    renderAuthPanel();

    try {
        const loginResult = await fetchApi("/api/v1/auth/login", {
            method: "POST",
            body: { usernameOrEmail, password }
        });
        setStoredToken(loginResult.accessToken);
        elements.passwordInput.value = "";
        await bootstrapSession();
    } catch (error) {
        console.warn("login failed", error);
        state.authMessage = `Login failed: ${error.message}`;
        renderAuthPanel();
    } finally {
        elements.loginButton.disabled = false;
    }
}

async function handleRefresh() {
    if (!state.session?.primaryPlayer) {
        state.authMessage = "This account does not have a primary player to refresh.";
        renderAuthPanel();
        return;
    }

    state.authMessage = "Refreshing live stats for the current primary player...";
    renderAuthPanel();

    try {
        await loadCurrentPlayerMatches();
        state.authMessage = "Live stats refreshed.";
    } catch (error) {
        console.warn("refresh failed", error);
        state.authMessage = `Refresh failed: ${error.message}`;
    }
    render();
}

async function handleUserAvatarUpload(event) {
    const file = event.target.files?.[0];
    if (!file) {
        return;
    }
    if (!state.token || !state.session) {
        state.authMessage = "Please login before uploading a user avatar.";
        renderAuthPanel();
        event.target.value = "";
        return;
    }

    const formData = new FormData();
    formData.append("file", file);

    state.authMessage = "Uploading user avatar...";
    renderAuthPanel();

    try {
        const result = await fetchApi("/api/v1/users/me/avatar", {
            method: "POST",
            auth: true,
            formData
        });
        state.session = {
            ...state.session,
            avatarUrl: result.avatarUrl
        };
        state.authMessage = "User avatar updated.";
    } catch (error) {
        console.warn("user avatar upload failed", error);
        state.authMessage = `User avatar upload failed: ${error.message}`;
    } finally {
        event.target.value = "";
        render();
    }
}

function handleLogout() {
    setStoredToken(null);
    state.session = null;
    state.dashboardAssets = createEmptyAssets();
    state.authMessage = "Logged out. Log in to view player data.";
    elements.usernameInput.value = "";
    elements.passwordInput.value = "";
    setMatches([], "LOCKED", "Log in to view live player data.");
}

function bindEvents() {
    elements.loginForm.addEventListener("submit", handleLogin);
    elements.refreshButton.addEventListener("click", handleRefresh);
    elements.logoutButton.addEventListener("click", handleLogout);
    elements.userAvatarInput.addEventListener("change", handleUserAvatarUpload);
}

function init() {
    bindEvents();
    bootstrapSession();
}

init();
