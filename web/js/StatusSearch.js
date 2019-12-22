
function StatusSearch(outobj,status,type)
{
  switch (type) {
    case "/user/login":
    {
      if(status==500)
        outobj.$message.error("错误信息");
    }
  }
}