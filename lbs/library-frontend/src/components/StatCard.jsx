function StatCard({
    title,
    value,
    icon,
    subtitle,
    variant = "purple"
}) {

    return (

        <article className="admin-stat-card">

            <div className="admin-stat-card-top">

                <div
                    className={
                        `admin-stat-icon admin-stat-icon-${variant}`
                    }
                >
                    {icon}
                </div>

                <span className="admin-stat-indicator">
                    LIVE
                </span>

            </div>


            <div className="admin-stat-content">

                <span className="admin-stat-title">
                    {title}
                </span>

                <strong className="admin-stat-value">
                    {value}
                </strong>

                <small className="admin-stat-subtitle">
                    {subtitle}
                </small>

            </div>

        </article>

    );
}


export default StatCard;