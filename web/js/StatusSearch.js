function StatusSearch(outobj,status,type)
{
    switch (type) {
        case "/user/register":
        {
            if(status==501)
                outobj.$message.error("用户名重复");
            if(status==555)
                outobj.$message.error("请求方法错误");
        }break;
        case "/user/login":
        {
            if(status==501)
                outobj.$message.error("用户名或密码错误");
            if(status==502)
                outobj.$message.error("ip不可用");
            if(status==555)
                outobj.$message.error("请求参数错误");
        }break;
        case "/user/checkrepeat":
        {
            if(status==500)
                outobj.$message.error("发生重复");
            if(status==555)
                outobj.$message.error("请求方法错误");
        }break;
        case "/child/getchilds":
        {
            if(status==556)
                outobj.$message.error("用户名登录状态不一致");
            if(status==557)
                outobj.$message.error("权限错误，当前用户没有权限");
            if(status==555)
                outobj.$message.error("请求方法错误");
        }break;
        case "/child/modifychildinfo":
        {
            if(status==500)
                outobj.$message.error("管理员密码输入错误");
            if(status==501)
                outobj.$message.error("逻辑节点不存在");
            if(status==502)
                outobj.$message.error("邮箱错误");
            if(status==503)
                outobj.$message.error("密码格式错误");
            if(status==557)
                outobj.$message.error("权限错误，当前用户没有权限");
            if(status==504)
                outobj.$message.error("子账号不存在");
            if(status==555)
                outobj.$message.error("请求方法错误");
        }break;
        case "/child/delchild":
        {
            if(status==556)
                outobj.$message.error("用户面登录状态不一致");
            if(status==557)
                outobj.$message.error("权限错误，当前用户没有权限");
            if(status==503)
                outobj.$message.error("主账号密码输入错误");
            if(status==502)
                outobj.$message.error("子账号错误");
        }break;
        case "/dir/dir":
        {
            if(status==557)
                outobj.$message.error("权限错误哦，当前用户没有权限");
            if(status==501)
                outobj.$message.error("递归层级超出最大深度");
            if(status==555)
                outobj.$message.error("请求方法错误");
        }break;
        case "/dir/newdir":
        {
            if(status==557)
                outobj.$message.error("权限错误，当前用户没有权限");
            if(status==501)
                outobj.$message.error("当前目录下存在重名文件");
            if(status==555)
                outobj.$message.error("请求方法错误");
        }break;
        case "/dir/modifydirname":
        {
            if(status==557)
                outobj.$message.error("权限错误，当前用户没有权限");
            if(status==501)
                outobj.$message.error("当前节点不存在");
            if(status==502)
                outobj.$message.error("存在重名");
            if(status==555)
                outobj.$message.error("请求方法错误");
        }break;
        case "/dir/modifydirposition":
        {
            if(status==557)
                outobj.$message.error("权限错误，当前用户没有权限");
            if(status==555)
                outobj.$message.error("方法调用错误");
            if(status==501)
                outobj.$message.error("修改的目录不存在");
        }break;
        case "/file/newfile":
        {
            if(status==557)
                outobj.$message.error("权限错误，当前用户没有权限");
            if(status==501)
                outobj.$message.error("新建文件的目录不存在");
            if(status==556)
                outobj.$message.error("用户名登录状态不一致");
            if(status==503)
                outobj.$message.error("当前目录下是否有重名文件");
            if(status==555)
                outobj.$message.error("请求方法错误");
        }break;
        case "/file/modifyfilename":
        {
            if(status==557)
                outobj.$message.error("权限错误，当前用户没有权限");
            if(status==501)
                outobj.$message.error("原始文件名称不一致");
            if(status==502)
                outobj.$message.error("文件不存在");
            if(status==503)
                outobj.$message.error("新文件与其他文件重名");
            if(status==555)
                outobj.$message.error("请求方法错误");
        }break;
        case "/file/getfiletree":
        {
            if(status==556)
                outobj.$message.error("用户名登录状态不一致");
            if(status==500)
                outobj.$message.error("当前目录节点不存在");
            if(status==557)
                outobj.$message.error("权限错误，当前用户没有权限");
            if(status==555)
                outobj.$message.error("方法调用方式错误");
        }break;
        case "/file/modifyfileposition":
        {
            if(status==556)
                outobj.$message.error("用户名登录状态不一致");
            if(status==557)
                outobj.$message.error("权限错误，当前用户没有权限");
            if(status==502)
                outobj.$message.error("移动没有完成可能是节点存在问题（找不到节点列表中某一个节点）");
            if(status==555)
                outobj.$message.error("请求方法错误");
        }break;
        case "/file/copyfile":
        {
            if(status==556)
                outobj.$message.error("用户名登录状态不一致");
            if(status==557)
                outobj.$message.error("权限错误，当前用户没有权限");
            if(status==501)
                outobj.$message.error("当前文件不再统一父节点下，无法复制");
            if(status==502)
                outobj.$message.error("当前文件没有操作权限");
            if(status==555)
                outobj.$message.error("接口调用失败");
        }break;
        case "/file/delfile":
        {
            if(status==556)
                outobj.$message.error("用户名登录状态不一致");
            if(status==501)
                outobj.$message.error("删除目录不存在");
            if(status==557)
                outobj.$message.error("权限错误，当前用户没有权限");
            if(status==555)
                outobj.$message.error("请求方法错误");
        }break;
        case "/chain/newchain":
        {
            if(status==556)
                outobj.$message.error("用户名登录状态不一致");
            if(status==557)
                outobj.$message.error("权限错误，当前用户没有权限");
            if(status==502)
                outobj.$message.error("节点不存在");
            if(status==555)
                outobj.$message.error("接口调用错误");
        }break;
        case "/chain/get":
        {
            if(status==502)
                outobj.$message.error("链接已经过期了");
            if(status==507)
                outobj.$message.error("权限错误，当前用户没有权限");
            if(status==555)
                outobj.$message.error("调用参数错误");
            if(status==501)
                outobj.$message.error("密码错误");
        }break;
        case "/chain/getall":
        {
            if(status==500)
                outobj.$message.error("用户未登录");
            if(status==501)
                outobj.$message.error("用户登录状态不一致");
            if(status==555)
                outobj.$message.error("接口调用错误");
        }break;
        case "/transfer/gettoken":
        {
            if(status==500)
                outobj.$message.error("用户权限错误");
            if(status==557)
                outobj.$message.error("权限错误，当前用户没有权限");
            if(status==502)
                outobj.$message.error("Token生成失败");
        }break;
        case "/transfer/download":
        {
            if(status==556)
                outobj.$message.error("用户登录状态不一致");
            if(status==557)
                outobj.$message.error("权限错误，当前用户没有权限");
            if(status==502)
                outobj.$message.error("当前节点不存在");
            if(status==501)
                outobj.$message.error("未知错误");
        }break;
        case "/file/getfiletree":
        {
            if(status==500)
                outobj.$message.error("当前目录不存在");
            if(status==557)
                outobj.$message.error("没有权限访问");
            if(status==501)
                outobj.$message.error("未知错误");
        }break;
    }
}