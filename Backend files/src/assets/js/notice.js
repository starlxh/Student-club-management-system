document.addEventListener("DOMContentLoaded", function () {
    // 模拟从服务器获取公告数据
    const announcements = [
        {
            title: "社团活动通知",
            date: "2024-12-10",
            content: "本周五将举行年终聚会，请大家准时参加，活动细节请查看社团群。"
        },
        {
            title: "招新面试安排",
            date: "2024-12-12",
            content: "社团招新面试将于下周三举行，具体时间和地点将在社团群内发布。"
        },
        {
            title: "社团发展计划",
            date: "2024-12-05",
            content: "社团的下一阶段发展计划已经制定，欢迎大家积极参与讨论。"
        }
    ];

    const announcementList = document.getElementById('announcement-list');

    announcements.forEach(function(announcement) {
        const div = document.createElement('div');
        div.classList.add('announcement');
        div.innerHTML = `
            <h3>${announcement.title}</h3>
            <p><strong>发布时间：</strong>${announcement.date}</p>
            <p>${announcement.content}</p>
        `;
        announcementList.appendChild(div);
    });
});
