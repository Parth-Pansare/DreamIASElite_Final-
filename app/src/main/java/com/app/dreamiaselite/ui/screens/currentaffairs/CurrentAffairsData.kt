package com.app.dreamiaselite.ui.screens.currentaffairs

/**
 * Temporary static data source for current affairs until backend/API is wired.
 * Shared between dashboard cards and the Current Affairs screen.
 */
object CurrentAffairsData {
    val articles: List<CurrentAffairArticle> = listOf(
        CurrentAffairArticle(
            id = 1,
            category = "Economy",
            importance = "High",
            title = "RBI Monetary Policy Review",
            summary = "Key repo rate stance, inflation outlook and liquidity measures with GS3 implications.",
            date = "Nov 28",
            readTime = "7 min read",
            tag = "Economy"
        ),
        CurrentAffairArticle(
            id = 2,
            category = "Environment & Ecology",
            importance = "High",
            title = "New Environment Treaty Signed",
            summary = "Global pact on biodiversity finance, climate goals and national commitments.",
            date = "Nov 24",
            readTime = "6 min read",
            tag = "Environment & Ecology"
        ),
        CurrentAffairArticle(
            id = 3,
            category = "Polity & Governance",
            importance = "High",
            title = "Supreme Court ruling on FRs",
            summary = "Landmark interpretation of Fundamental Rights and its impact on executive powers.",
            date = "Nov 22",
            readTime = "5 min read",
            tag = "Polity & Governance"
        ),
        CurrentAffairArticle(
            id = 4,
            category = "Economy",
            importance = "Moderate",
            title = "India’s GDP forecast updated",
            summary = "Revised projections, key drivers and sectoral outlook for FY25.",
            date = "Nov 21",
            readTime = "6 min read",
            tag = "Economy"
        ),
        CurrentAffairArticle(
            id = 5,
            category = "Agriculture",
            importance = "Moderate",
            title = "New MSP changes explained",
            summary = "Breakdown of MSP revisions, crops covered and fiscal impact for states.",
            date = "Nov 19",
            readTime = "5 min read",
            tag = "Agriculture"
        ),
        CurrentAffairArticle(
            id = 6,
            category = "Environment & Ecology",
            importance = "Moderate",
            title = "Blue Economy push",
            summary = "Maritime infrastructure, coastal livelihoods and sustainability focus.",
            date = "Nov 18",
            readTime = "6 min read",
            tag = "Environment & Ecology"
        ),
        CurrentAffairArticle(
            id = 7,
            category = "Science & Tech",
            importance = "High",
            title = "AI regulation paper",
            summary = "Proposed guardrails for AI use, ethical frameworks and global benchmarks.",
            date = "Nov 17",
            readTime = "7 min read",
            tag = "Science & Tech"
        ),
        CurrentAffairArticle(
            id = 8,
            category = "International Relations",
            importance = "Moderate",
            title = "G20 outcomes tracker",
            summary = "Follow-up on summit communique, climate finance and digital public goods.",
            date = "Nov 16",
            readTime = "6 min read",
            tag = "International Relations"
        ),
        CurrentAffairArticle(
            id = 9,
            category = "Environment & Ecology",
            importance = "Moderate",
            title = "Mission LiFE updates",
            summary = "State-level adoption, behavioural change metrics and new pilot programs.",
            date = "Nov 15",
            readTime = "5 min read",
            tag = "Environment & Ecology"
        ),
        CurrentAffairArticle(
            id = 10,
            category = "International Relations",
            importance = "Moderate",
            title = "NATO expansion brief",
            summary = "Accession timeline, regional security implications and India’s stance.",
            date = "Nov 14",
            readTime = "5 min read",
            tag = "International Relations"
        ),
        CurrentAffairArticle(
            id = 11,
            category = "Science & Tech",
            importance = "Moderate",
            title = "Quantum tech roadmap",
            summary = "National mission milestones, funding and strategic applications.",
            date = "Nov 13",
            readTime = "7 min read",
            tag = "Science & Tech"
        ),
        CurrentAffairArticle(
            id = 12,
            category = "Environment & Ecology",
            importance = "High",
            title = "Forest (Amendment) Act",
            summary = "Core provisions, exemptions and compliance pathways for states.",
            date = "Nov 12",
            readTime = "6 min read",
            tag = "Environment & Ecology"
        )
    )

    fun getById(id: Int): CurrentAffairArticle? = articles.find { it.id == id }
}
