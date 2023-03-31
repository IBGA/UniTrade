function getNumOfDays(dateStr) {
    const publishDate = new Date(dateStr);
    const diff = new Date() - publishDate.getTime();
    const days = diff / (1000 * 3600 * 24);
    const hours = diff / (1000 * 3600);
    const minutes = hours * 60;
    
    if (days >= 365) return `${Math.floor(days / 365)} years ago`
    if (days >= 14) return `${Math.floor(days / 7)} weeks ago`
    if (days <= 1 && hours > 1) return `${Math.floor(hours)} hours ago`
    if (hours <= 1) return `${Math.floor(minutes)} minutes ago`
    return `${floor(days)} days ago`
}

export {getNumOfDays}