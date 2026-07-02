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
    boundPlayers: [],
    dashboardAssets: createEmptyAssets(),
    adminOverview: null,
    authMode: "login",
    authMessage: "After login or registration, the page will load the current account and its primary player automatically.",
    isLoading: false,
    isSwitchingPlayer: false
};

const queryParams = new URLSearchParams(window.location.search);

const elements = {
    brandLogo: document.getElementById("brandLogo"),
    brandFallback: document.getElementById("brandFallback"),
    loggedInPanel: document.getElementById("loggedInPanel"),
    authSwitcher: document.getElementById("authSwitcher"),
    loginModeButton: document.getElementById("loginModeButton"),
    registerModeButton: document.getElementById("registerModeButton"),
    loginForm: document.getElementById("loginForm"),
    registerForm: document.getElementById("registerForm"),
    usernameInput: document.getElementById("usernameInput"),
    passwordInput: document.getElementById("passwordInput"),
    loginButton: document.getElementById("loginButton"),
    registerUsernameInput: document.getElementById("registerUsernameInput"),
    registerEmailInput: document.getElementById("registerEmailInput"),
    registerDisplayNameInput: document.getElementById("registerDisplayNameInput"),
    registerPasswordInput: document.getElementById("registerPasswordInput"),
    registerConfirmPasswordInput: document.getElementById("registerConfirmPasswordInput"),
    registerButton: document.getElementById("registerButton"),
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
    playerAvatarInput: document.getElementById("playerAvatarInput"),
    playerAvatarUploadButton: document.getElementById("playerAvatarUploadButton"),
    playerSwitcher: document.getElementById("playerSwitcher"),
    playerSwitcherHint: document.getElementById("playerSwitcherHint"),
    playerSwitcherList: document.getElementById("playerSwitcherList"),
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
    loadoutBars: document.getElementById("loadoutBars"),
    adminPanel: document.getElementById("adminPanel"),
    adminRefreshButton: document.getElementById("adminRefreshButton"),
    adminSummary: document.getElementById("adminSummary"),
    adminUserList: document.getElementById("adminUserList")
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

function createEmptyAdminOverview() {
    return {
        totalUsers: 0,
        totalPlayers: 0,
        totalTrackedMatches: 0,
        users: []
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

function setImageSource(element, url) {
    element.src = url || BLANK_IMAGE;
}

function formatPercent(value) {
    return `${Number(value || 0).toFixed(1)}%`;
}

function formatDuration(seconds) {
    const minutes = Math.max(0, Math.round((seconds || 0) / 60));
    return `${minutes}m`;
}

function formatDateTime(input) {
    if (!input) {
        return "-";
    }
    const date = new Date(input);
    if (Number.isNaN(date.getTime())) {
        return "-";
    }
    return `${date.getMonth() + 1}/${date.getDate()} ${String(date.getHours()).padStart(2, "0")}:${String(date.getMinutes()).padStart(2, "0")}`;
}

function formatMetric(value, digits = 1, suffix = "") {
    if (value === null || value === undefined || value === "") {
        return "-";
    }
    const numeric = Number(value);
    if (Number.isNaN(numeric)) {
        return "-";
    }
    return `${numeric.toFixed(digits)}${suffix}`;
}

function formatCount(value) {
    if (value === null || value === undefined) {
        return "0";
    }
    return `${value}`;
}

function escapeHtml(value) {
    return String(value ?? "")
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#39;");
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

function sortPlayers(players) {
    return [...(players || [])].sort((left, right) => {
        const primaryGap = Number(Boolean(right.primary)) - Number(Boolean(left.primary));
        if (primaryGap !== 0) {
            return primaryGap;
        }
        return Number(left.playerId || 0) - Number(right.playerId || 0);
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

function setAuthMode(mode) {
    state.authMode = mode === "register" ? "register" : "login";
    renderAuthMode();
}

function renderAuthMode() {
    const isRegisterMode = state.authMode === "register";
    elements.loginForm.classList.toggle("is-hidden", isRegisterMode || !!state.session);
    elements.registerForm.classList.toggle("is-hidden", !isRegisterMode || !!state.session);
    elements.loginModeButton.classList.toggle("is-active", !isRegisterMode);
    elements.registerModeButton.classList.toggle("is-active", isRegisterMode);
}

function renderAuthPanel() {
    const user = state.session;
    if (!user) {
        elements.loggedInPanel.classList.add("is-hidden");
        elements.authSwitcher.classList.remove("is-hidden");
        renderAuthMode();
        elements.authMessage.textContent = state.authMessage;
        elements.sessionStatus.textContent = state.isLoading ? "Checking" : "Logged Out";
        return;
    }

    const currentPlayer = user.primaryPlayer;
    const avatarUrl = user.avatarUrl || currentPlayer?.avatarUrl || getDefaultAvatarUrl();
    elements.loggedInPanel.classList.remove("is-hidden");
    elements.authSwitcher.classList.add("is-hidden");
    elements.loginForm.classList.add("is-hidden");
    elements.registerForm.classList.add("is-hidden");
    setImageSource(elements.authAvatar, avatarUrl);
    elements.authDisplayName.textContent = user.displayName || user.username;
    elements.authSubtitle.textContent = currentPlayer
        ? `${currentPlayer.fullName || currentPlayer.gameName} - ${currentPlayer.rankTier || "Unranked"}`
        : user.roleCode === "ADMIN"
            ? "Administrator session is active. User overview is available below."
            : "No primary player is bound to this account yet.";
    elements.roleBadge.textContent = user.roleCode || "USER";
    elements.authMessage.textContent = state.authMessage;
    elements.sessionStatus.textContent = currentPlayer ? "Live" : user.roleCode === "ADMIN" ? "Admin" : "No Player";
}

function renderPlayerCard() {
    if (state.session?.roleCode === "ADMIN") {
        elements.playerCardPanel.classList.add("is-hidden");
        return;
    }

    const currentPlayer = state.session?.primaryPlayer;
    if (state.session && !currentPlayer) {
        elements.playerCardPanel.classList.add("is-hidden");
        return;
    }

    elements.playerCardPanel.classList.remove("is-hidden");
    if (!currentPlayer) {
        setImageSource(elements.playerAvatar, getDefaultAvatarUrl());
        elements.playerName.textContent = "Waiting For Login";
        elements.playerMeta.textContent = "The dashboard will resolve the current account's primary player.";
        elements.playerRank.textContent = "-";
        elements.playerPlatform.textContent = "-";
        elements.playerLevel.textContent = "-";
        elements.playerRegion.textContent = "-";
        elements.playerAvatarUploadButton.classList.add("is-hidden");
        return;
    }

    setImageSource(elements.playerAvatar, currentPlayer.avatarUrl || getDefaultAvatarUrl());
    elements.playerName.textContent = currentPlayer.fullName || currentPlayer.gameName || "Unnamed Player";
    elements.playerMeta.textContent = `Player ID #${currentPlayer.playerId} - ${currentPlayer.status || "ACTIVE"}`;
    elements.playerRank.textContent = currentPlayer.rankTier || "-";
    elements.playerPlatform.textContent = currentPlayer.platform || "-";
    elements.playerLevel.textContent = currentPlayer.accountLevel ?? "-";
    elements.playerRegion.textContent = currentPlayer.regionCode || "-";
    elements.playerAvatarUploadButton.classList.remove("is-hidden");
}

function renderPlayerSwitcher() {
    if (!state.session) {
        elements.playerSwitcher.classList.add("is-hidden");
        elements.playerSwitcherList.innerHTML = "";
        return;
    }

    elements.playerSwitcher.classList.remove("is-hidden");

    if (!state.boundPlayers.length) {
        elements.playerSwitcherHint.textContent = "No game account is bound yet. Bind one to load live stats.";
        elements.playerSwitcherList.innerHTML = "";
        return;
    }

    elements.playerSwitcherHint.textContent = state.boundPlayers.length > 1
        ? "Choose another bound account to switch the current dashboard view."
        : "This account currently has one bound game account.";

    const currentPlayerId = state.session?.primaryPlayer?.playerId;
    elements.playerSwitcherList.innerHTML = state.boundPlayers.map((player) => `
        <button
            class="player-switcher__button ${player.playerId === currentPlayerId ? "is-active" : ""}"
            type="button"
            data-player-id="${player.playerId}"
            ${state.isSwitchingPlayer ? "disabled" : ""}
        >
            <div class="player-switcher__main">
                <strong>${escapeHtml(player.fullName || player.gameName || "Unnamed Player")}</strong>
                <span>${escapeHtml(player.platform || "-")} | ${escapeHtml(player.regionCode || "-")} | ${escapeHtml(player.rankTier || "Unranked")}</span>
            </div>
            <div class="player-switcher__meta">
                ${player.primary ? '<span class="player-switcher__pill is-primary">Primary</span>' : ""}
                <span>Lv.${escapeHtml(player.accountLevel ?? "-")}</span>
            </div>
        </button>
    `).join("");

    elements.playerSwitcherList.querySelectorAll("[data-player-id]").forEach((button) => {
        button.addEventListener("click", () => {
            handlePlayerActivate(Number(button.dataset.playerId));
        });
    });
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

function renderAdminPanel() {
    const isAdmin = state.session?.roleCode === "ADMIN";
    if (!isAdmin) {
        elements.adminPanel.classList.add("is-hidden");
        elements.adminSummary.innerHTML = "";
        elements.adminUserList.innerHTML = "";
        return;
    }

    const overview = state.adminOverview || createEmptyAdminOverview();
    elements.adminPanel.classList.remove("is-hidden");
    elements.adminSummary.innerHTML = [
        { label: "Users", value: overview.totalUsers },
        { label: "Bound Accounts", value: overview.totalPlayers },
        { label: "Tracked Matches", value: overview.totalTrackedMatches }
    ].map((item) => `
        <div class="admin-summary__card">
            <span>${item.label}</span>
            <strong>${formatCount(item.value)}</strong>
        </div>
    `).join("");

    if (!overview.users.length) {
        elements.adminUserList.innerHTML = `<div class="admin-empty">No user data is available yet.</div>`;
        return;
    }

    elements.adminUserList.innerHTML = overview.users.map((user) => renderAdminUserCard(user)).join("");
}

function renderAdminUserCard(user) {
    const playersMarkup = user.players?.length
        ? user.players.map((player) => renderAdminPlayerCard(player)).join("")
        : `<div class="admin-empty">This user does not have a bound VALORANT account yet.</div>`;

    return `
        <article class="admin-user-card">
            <div class="admin-user__header">
                <div class="admin-user__identity">
                    <img class="admin-user__avatar" src="${escapeHtml(user.avatarUrl || BLANK_IMAGE)}" alt="User avatar">
                    <div>
                        <strong>${escapeHtml(user.displayName || user.username || "Unnamed User")}</strong>
                        <p>${escapeHtml(user.username || "-")} | ${escapeHtml(user.email || "No email")}</p>
                    </div>
                </div>
                <div class="admin-user__pills">
                    <span class="status-pill ${user.roleCode === "ADMIN" ? "status-pill--admin" : ""}">${escapeHtml(user.roleCode || "USER")}</span>
                    <span class="status-pill">${escapeHtml(user.status || "ACTIVE")}</span>
                </div>
            </div>
            <div class="admin-user__facts">
                <div class="admin-fact">
                    <span>Registered</span>
                    <strong>${escapeHtml(formatDateTime(user.createdAt))}</strong>
                </div>
                <div class="admin-fact">
                    <span>Last Login</span>
                    <strong>${escapeHtml(formatDateTime(user.lastLoginAt))}</strong>
                </div>
                <div class="admin-fact">
                    <span>Bound Accounts</span>
                    <strong>${formatCount(user.players?.length || 0)}</strong>
                </div>
                <div class="admin-fact">
                    <span>User ID</span>
                    <strong>#${formatCount(user.userId)}</strong>
                </div>
            </div>
            <div class="admin-player-list">${playersMarkup}</div>
        </article>
    `;
}

function renderAdminPlayerCard(player) {
    return `
        <article class="admin-player-card">
            <div class="admin-player__header">
                <div class="admin-player__identity">
                    <div>
                        <strong>${escapeHtml(player.fullName || player.gameName || "Unnamed Player")}</strong>
                        <p>${escapeHtml(player.platform || "-")} | ${escapeHtml(player.regionCode || "-")} | ${escapeHtml(player.rankTier || "Unranked")}</p>
                    </div>
                </div>
                <div class="admin-player__pills">
                    ${player.primary ? '<span class="status-pill status-pill--primary">Primary</span>' : ""}
                    <span class="status-pill">${escapeHtml(player.status || "ACTIVE")}</span>
                </div>
            </div>
            <div class="admin-player__stats">
                <div class="admin-stat">
                    <span>Matches</span>
                    <strong>${formatCount(player.matchCount)}</strong>
                </div>
                <div class="admin-stat">
                    <span>Avg ACS</span>
                    <strong>${escapeHtml(formatMetric(player.averageAcs, 1))}</strong>
                </div>
                <div class="admin-stat">
                    <span>KD</span>
                    <strong>${escapeHtml(formatMetric(player.kdRatio, 2))}</strong>
                </div>
                <div class="admin-stat">
                    <span>Avg K / D / A</span>
                    <strong>${escapeHtml(`${formatMetric(player.averageKills, 1)} / ${formatMetric(player.averageDeaths, 1)} / ${formatMetric(player.averageAssists, 1)}`)}</strong>
                </div>
                <div class="admin-stat">
                    <span>Win Rate</span>
                    <strong>${escapeHtml(formatMetric(player.winRate, 1, "%"))}</strong>
                </div>
                <div class="admin-stat">
                    <span>HS Rate</span>
                    <strong>${escapeHtml(formatMetric(player.headshotRate, 1, "%"))}</strong>
                </div>
                <div class="admin-stat">
                    <span>KAST</span>
                    <strong>${escapeHtml(formatMetric(player.kastRate, 1, "%"))}</strong>
                </div>
                <div class="admin-stat">
                    <span>Account Level</span>
                    <strong>${escapeHtml(player.accountLevel ?? "-")}</strong>
                </div>
                <div class="admin-stat">
                    <span>Player ID</span>
                    <strong>#${formatCount(player.playerId)}</strong>
                </div>
                <div class="admin-stat">
                    <span>Last Match</span>
                    <strong>${escapeHtml(formatDateTime(player.lastMatchAt))}</strong>
                </div>
            </div>
        </article>
    `;
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
    renderPlayerSwitcher();
    renderAdminPanel();
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

async function loadBoundPlayers() {
    if (!state.session) {
        state.boundPlayers = [];
        return;
    }

    const players = await fetchApi("/api/v1/players/me", { auth: true });
    state.boundPlayers = sortPlayers(players || []);

    const currentPlayerId = state.session.primaryPlayer?.playerId;
    if (!currentPlayerId && state.boundPlayers[0]) {
        state.session = {
            ...state.session,
            primaryPlayer: state.boundPlayers[0]
        };
        return;
    }

    const currentPlayer = state.boundPlayers.find((player) => player.playerId === currentPlayerId);
    if (currentPlayer) {
        state.session = {
            ...state.session,
            primaryPlayer: currentPlayer
        };
    }
}

async function loadAdminOverview() {
    if (state.session?.roleCode !== "ADMIN") {
        state.adminOverview = null;
        return;
    }
    state.adminOverview = await fetchApi("/api/v1/admin/users/overview", { auth: true });
    renderAdminPanel();
}

async function bootstrapSession() {
    if (!state.token) {
        state.session = null;
        state.boundPlayers = [];
        state.dashboardAssets = createEmptyAssets();
        state.adminOverview = null;
        state.authMessage = shouldFallbackToLocalApi()
            ? `Current page is using backend API ${describeApiBase()}. Log in to view live player data.`
            : "Log in to view live player data.";
        setMatches(previewMatches, "PREVIEW", "Preview mode is shown before login. Live data will replace it after authentication.");
        return;
    }

    state.isLoading = true;
    renderAuthPanel();

    try {
        const sessionContext = await fetchApi("/api/v1/auth/session-context", { auth: true });
        state.session = sessionContext;
        state.boundPlayers = [];
        state.dashboardAssets = sessionContext.dashboardAssets || createEmptyAssets();
        state.adminOverview = null;
        state.authMessage = sessionContext.primaryPlayer
            ? "Session ready. Loading live stats for the current primary player."
            : sessionContext.roleCode === "ADMIN"
                ? "Administrator session ready. Loading user overview."
                : "Session ready, but this account does not have a primary player yet.";

        await loadBoundPlayers();
        await loadCurrentPlayerMatches();

        if (sessionContext.roleCode === "ADMIN") {
            try {
                await loadAdminOverview();
                state.authMessage = sessionContext.primaryPlayer
                    ? "Session ready. Live stats and admin overview loaded."
                    : "Administrator session ready. User overview loaded.";
            } catch (adminError) {
                console.warn("admin overview failed", adminError);
                state.adminOverview = createEmptyAdminOverview();
                state.authMessage = `Admin overview failed to load: ${adminError.message}`;
            }
        }
    } catch (error) {
        console.warn("session bootstrap failed", error);
        setStoredToken(null);
        state.session = null;
        state.boundPlayers = [];
        state.dashboardAssets = createEmptyAssets();
        state.adminOverview = null;
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

async function handleRegister(event) {
    event.preventDefault();
    const username = elements.registerUsernameInput.value.trim();
    const email = elements.registerEmailInput.value.trim();
    const displayName = elements.registerDisplayNameInput.value.trim();
    const password = elements.registerPasswordInput.value;
    const confirmPassword = elements.registerConfirmPasswordInput.value;

    if (!username || !email || !password || !confirmPassword) {
        state.authMessage = "Please complete username, email, password and password confirmation.";
        renderAuthPanel();
        return;
    }
    if (password.length < 8) {
        state.authMessage = "Password must be at least 8 characters long.";
        renderAuthPanel();
        return;
    }
    if (password !== confirmPassword) {
        state.authMessage = "The two passwords do not match.";
        renderAuthPanel();
        return;
    }

    elements.registerButton.disabled = true;
    state.authMessage = "Creating account and starting session...";
    renderAuthPanel();

    try {
        const registerResult = await fetchApi("/api/v1/auth/register", {
            method: "POST",
            body: {
                username,
                email,
                displayName: displayName || null,
                password
            }
        });
        setStoredToken(registerResult.accessToken);
        clearRegisterForm();
        await bootstrapSession();
    } catch (error) {
        console.warn("register failed", error);
        state.authMessage = `Register failed: ${error.message}`;
        renderAuthPanel();
    } finally {
        elements.registerButton.disabled = false;
    }
}

async function handleRefresh() {
    const shouldRefreshPlayer = Boolean(state.session?.primaryPlayer);
    const shouldRefreshAdmin = state.session?.roleCode === "ADMIN";

    if (!shouldRefreshPlayer && !shouldRefreshAdmin) {
        state.authMessage = "This account does not have data to refresh yet.";
        renderAuthPanel();
        return;
    }

    state.authMessage = shouldRefreshAdmin
        ? "Refreshing live stats and admin overview..."
        : "Refreshing live stats for the current primary player...";
    renderAuthPanel();

    try {
        await loadBoundPlayers();
        if (shouldRefreshPlayer) {
            await loadCurrentPlayerMatches();
        }
        if (shouldRefreshAdmin) {
            await loadAdminOverview();
        }
        state.authMessage = shouldRefreshAdmin ? "Live stats and admin overview refreshed." : "Live stats refreshed.";
    } catch (error) {
        console.warn("refresh failed", error);
        state.authMessage = `Refresh failed: ${error.message}`;
    }
    render();
}

async function handlePlayerActivate(playerId) {
    if (!state.session || !playerId || state.isSwitchingPlayer) {
        return;
    }
    if (state.session.primaryPlayer?.playerId === playerId) {
        return;
    }

    state.isSwitchingPlayer = true;
    state.authMessage = "Switching current game account...";
    render();

    try {
        const currentPlayer = await fetchApi(`/api/v1/players/${playerId}/activate`, {
            method: "POST",
            auth: true
        });
        state.session = {
            ...state.session,
            primaryPlayer: currentPlayer
        };
        await loadBoundPlayers();
        await loadCurrentPlayerMatches();
        state.authMessage = `Switched to ${currentPlayer.fullName || currentPlayer.gameName}.`;
    } catch (error) {
        console.warn("player activate failed", error);
        state.authMessage = `Account switch failed: ${error.message}`;
    } finally {
        state.isSwitchingPlayer = false;
        render();
    }
}

async function handleAdminRefresh() {
    if (state.session?.roleCode !== "ADMIN") {
        return;
    }

    elements.adminRefreshButton.disabled = true;
    state.authMessage = "Refreshing admin overview...";
    renderAuthPanel();

    try {
        await loadAdminOverview();
        state.authMessage = "Admin overview refreshed.";
    } catch (error) {
        console.warn("admin refresh failed", error);
        state.authMessage = `Admin refresh failed: ${error.message}`;
    } finally {
        elements.adminRefreshButton.disabled = false;
        render();
    }
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
        if (state.session.roleCode === "ADMIN") {
            await loadAdminOverview();
        }
    } catch (error) {
        console.warn("user avatar upload failed", error);
        state.authMessage = `User avatar upload failed: ${error.message}`;
    } finally {
        event.target.value = "";
        render();
    }
}

async function handlePlayerAvatarUpload(event) {
    const file = event.target.files?.[0];
    if (!file) {
        return;
    }
    const currentPlayer = state.session?.primaryPlayer;
    if (!state.token || !state.session || !currentPlayer?.playerId) {
        state.authMessage = "Please select a current game account before uploading a player avatar.";
        renderAuthPanel();
        event.target.value = "";
        return;
    }

    const formData = new FormData();
    formData.append("file", file);

    state.authMessage = "Uploading player avatar...";
    renderAuthPanel();

    try {
        const result = await fetchApi(`/api/v1/players/${currentPlayer.playerId}/avatar`, {
            method: "POST",
            auth: true,
            formData
        });

        const nextPrimaryPlayer = {
            ...currentPlayer,
            avatarUrl: result.avatarUrl
        };

        state.session = {
            ...state.session,
            primaryPlayer: nextPrimaryPlayer
        };

        state.boundPlayers = state.boundPlayers.map((player) => {
            if (player.playerId !== currentPlayer.playerId) {
                return player;
            }
            return {
                ...player,
                avatarUrl: result.avatarUrl
            };
        });

        state.authMessage = "Player avatar updated.";
    } catch (error) {
        console.warn("player avatar upload failed", error);
        state.authMessage = `Player avatar upload failed: ${error.message}`;
    } finally {
        event.target.value = "";
        render();
    }
}

function clearRegisterForm() {
    elements.registerUsernameInput.value = "";
    elements.registerEmailInput.value = "";
    elements.registerDisplayNameInput.value = "";
    elements.registerPasswordInput.value = "";
    elements.registerConfirmPasswordInput.value = "";
}

function handleLogout() {
    setStoredToken(null);
    state.session = null;
    state.boundPlayers = [];
    state.dashboardAssets = createEmptyAssets();
    state.adminOverview = null;
    state.authMessage = "Logged out. Log in to view player data.";
    elements.usernameInput.value = "";
    elements.passwordInput.value = "";
    clearRegisterForm();
    setAuthMode("login");
    setMatches(previewMatches, "PREVIEW", "Preview mode is shown before login. Live data will replace it after authentication.");
}

function bindEvents() {
    elements.loginModeButton.addEventListener("click", () => setAuthMode("login"));
    elements.registerModeButton.addEventListener("click", () => setAuthMode("register"));
    elements.loginForm.addEventListener("submit", handleLogin);
    elements.registerForm.addEventListener("submit", handleRegister);
    elements.refreshButton.addEventListener("click", handleRefresh);
    elements.adminRefreshButton.addEventListener("click", handleAdminRefresh);
    elements.logoutButton.addEventListener("click", handleLogout);
    elements.userAvatarInput.addEventListener("change", handleUserAvatarUpload);
    elements.playerAvatarInput.addEventListener("change", handlePlayerAvatarUpload);
}

function init() {
    bindEvents();
    bootstrapSession();
}

init();
