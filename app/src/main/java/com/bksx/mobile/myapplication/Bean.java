package com.bksx.mobile.myapplication;

import java.util.List;

public class Bean {

    /**
     * returnCode : 200
     * returnMsg : 查询数据成功
     * returnData : {"yz":"6","tgqs":[{"ry":"3","sj":"2020年-03月-10日","lz":"1"},{"ry":"6","sj":"2020年-03月-11日","lz":"4"},{"ry":"7","sj":"2020年-03月-12日","lz":"4"},{"ry":"7","sj":"2020年-03月-13日","lz":"4"},{"ry":"7","sj":"2020年-03月-14日","lz":"4"},{"ry":"7","sj":"2020年-03月-15日","lz":"4"},{"ry":"7","sj":"2020年-03月-16日","lz":"4"},{"ry":"8","sj":"2020年-03月-17日","lz":"4"},{"ry":"8","sj":"2020年-03月-18日","lz":"5"},{"ry":"8","sj":"2020年-03月-19日","lz":"5"},{"ry":"8","sj":"2020年-03月-20日","lz":"5"},{"ry":"9","sj":"2020年-03月-21日","lz":"5"}],"rkzs":"9","xzlz":"0","gzry":"2","dqsj":"2020年03月21日 21:02","clsl":"2","gs":[],"zk":"3","drzj":"0","lzzs":"5","xzrs":"0","sq":[{"xxzs":"9","xzry":"1","mc":"三羊里一小区","xzlz":"0","lzdr":"0"}]}
     */

    private String returnCode;
    private String returnMsg;
    private ReturnDataBean returnData;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public ReturnDataBean getReturnData() {
        return returnData;
    }

    public void setReturnData(ReturnDataBean returnData) {
        this.returnData = returnData;
    }

    public static class ReturnDataBean {
        /**
         * yz : 6
         * tgqs : [{"ry":"3","sj":"2020年-03月-10日","lz":"1"},{"ry":"6","sj":"2020年-03月-11日","lz":"4"},{"ry":"7","sj":"2020年-03月-12日","lz":"4"},{"ry":"7","sj":"2020年-03月-13日","lz":"4"},{"ry":"7","sj":"2020年-03月-14日","lz":"4"},{"ry":"7","sj":"2020年-03月-15日","lz":"4"},{"ry":"7","sj":"2020年-03月-16日","lz":"4"},{"ry":"8","sj":"2020年-03月-17日","lz":"4"},{"ry":"8","sj":"2020年-03月-18日","lz":"5"},{"ry":"8","sj":"2020年-03月-19日","lz":"5"},{"ry":"8","sj":"2020年-03月-20日","lz":"5"},{"ry":"9","sj":"2020年-03月-21日","lz":"5"}]
         * rkzs : 9
         * xzlz : 0
         * gzry : 2
         * dqsj : 2020年03月21日 21:02
         * clsl : 2
         * gs : []
         * zk : 3
         * drzj : 0
         * lzzs : 5
         * xzrs : 0
         * sq : [{"xxzs":"9","xzry":"1","mc":"三羊里一小区","xzlz":"0","lzdr":"0"}]
         */

        private String yz;
        private String rkzs;
        private String xzlz;
        private String gzry;
        private String dqsj;
        private String clsl;
        private String zk;
        private String drzj;
        private String lzzs;
        private String xzrs;
        private List<TgqsBean> tgqs;
        private List<?> gs;
        private List<SqBean> sq;

        public String getYz() {
            return yz;
        }

        public void setYz(String yz) {
            this.yz = yz;
        }

        public String getRkzs() {
            return rkzs;
        }

        public void setRkzs(String rkzs) {
            this.rkzs = rkzs;
        }

        public String getXzlz() {
            return xzlz;
        }

        public void setXzlz(String xzlz) {
            this.xzlz = xzlz;
        }

        public String getGzry() {
            return gzry;
        }

        public void setGzry(String gzry) {
            this.gzry = gzry;
        }

        public String getDqsj() {
            return dqsj;
        }

        public void setDqsj(String dqsj) {
            this.dqsj = dqsj;
        }

        public String getClsl() {
            return clsl;
        }

        public void setClsl(String clsl) {
            this.clsl = clsl;
        }

        public String getZk() {
            return zk;
        }

        public void setZk(String zk) {
            this.zk = zk;
        }

        public String getDrzj() {
            return drzj;
        }

        public void setDrzj(String drzj) {
            this.drzj = drzj;
        }

        public String getLzzs() {
            return lzzs;
        }

        public void setLzzs(String lzzs) {
            this.lzzs = lzzs;
        }

        public String getXzrs() {
            return xzrs;
        }

        public void setXzrs(String xzrs) {
            this.xzrs = xzrs;
        }

        public List<TgqsBean> getTgqs() {
            return tgqs;
        }

        public void setTgqs(List<TgqsBean> tgqs) {
            this.tgqs = tgqs;
        }

        public List<?> getGs() {
            return gs;
        }

        public void setGs(List<?> gs) {
            this.gs = gs;
        }

        public List<SqBean> getSq() {
            return sq;
        }

        public void setSq(List<SqBean> sq) {
            this.sq = sq;
        }

        public static class TgqsBean {
            /**
             * ry : 3
             * sj : 2020年-03月-10日
             * lz : 1
             */

            private String ry;
            private String sj;
            private String lz;

            public String getRy() {
                return ry;
            }

            public void setRy(String ry) {
                this.ry = ry;
            }

            public String getSj() {
                return sj;
            }

            public void setSj(String sj) {
                this.sj = sj;
            }

            public String getLz() {
                return lz;
            }

            public void setLz(String lz) {
                this.lz = lz;
            }
        }

        public static class SqBean {
            /**
             * xxzs : 9
             * xzry : 1
             * mc : 三羊里一小区
             * xzlz : 0
             * lzdr : 0
             */

            private String xxzs;
            private String xzry;
            private String mc;
            private String xzlz;
            private String lzdr;

            public String getXxzs() {
                return xxzs;
            }

            public void setXxzs(String xxzs) {
                this.xxzs = xxzs;
            }

            public String getXzry() {
                return xzry;
            }

            public void setXzry(String xzry) {
                this.xzry = xzry;
            }

            public String getMc() {
                return mc;
            }

            public void setMc(String mc) {
                this.mc = mc;
            }

            public String getXzlz() {
                return xzlz;
            }

            public void setXzlz(String xzlz) {
                this.xzlz = xzlz;
            }

            public String getLzdr() {
                return lzdr;
            }

            public void setLzdr(String lzdr) {
                this.lzdr = lzdr;
            }
        }
    }
}
