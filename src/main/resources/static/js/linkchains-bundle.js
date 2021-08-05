let linkchains = window.linkchains();

async function verifyBadge(badge) {

    if (badge.verification.type === "MerQLVerification2020" && badge.signature.type === "ETHMerQL") {
        var metadata = {};
        metadata.anchor = badge.signature;
        metadata.indexhash = badge.signature.indexhash;
        metadata.settings = badge.signature.settings;

        delete metadata.anchor.indexhash;

        delete metadata.anchor.settings;

        delete badge.verification;

        delete badge.signature;

        var options = ok; // content of config-readonly.json

        var verifyResult = await linkchains.verify(badge, metadata, options);


// verifyResult will be an object of the form


// {


// verified: [ ... ],


// unverified: [ ... ]


// }

// - if unverified is an empty array, then verification succeeded ("true”),otherwise it failed (“false”).


//


// (There's an alternative way to build the metadata object that gives more granular verification than just "true/false":


// var granularMetadata = await linkchains.getGranularVerificationMetadata(badge, metadata);


// var verifyResult = await linkchains.verify(badge, granularMetadata, options);


//will give a verifyResult object where the verified array contains the individual data items in the badge which *did* verify,


// and unverified contains those that didn't. Individual data items=RDF triples/quads, but they should be understandable.


//)


    }
}