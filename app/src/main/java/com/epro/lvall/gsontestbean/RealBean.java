package com.epro.lvall.gsontestbean;

import java.util.List;

/**
 * @author zzy
 * @date 2021/8/27
 */
public class RealBean {

    /**
     * result : {"list":[{"id":4,"parent_id":0,"name":"陶瓷","typeTemplate":{"id":53,"name":null,"spec_ids":null,"brand_ids":null,"custom_attribute_items":null,"del":null},"del":null,"type_id":"53","hasChildren":false,"treeParent":null,"uuid":null},{"id":5,"parent_id":0,"name":"国画","typeTemplate":{"id":52,"name":null,"spec_ids":null,"brand_ids":null,"custom_attribute_items":null,"del":null},"del":null,"type_id":"52","hasChildren":true,"treeParent":null,"uuid":null},{"id":6,"parent_id":0,"name":"金银","typeTemplate":{"id":53,"name":null,"spec_ids":null,"brand_ids":null,"custom_attribute_items":null,"del":null},"del":null,"type_id":"53","hasChildren":true,"treeParent":null,"uuid":null},{"id":11,"parent_id":0,"name":"手和","typeTemplate":{"id":52,"name":null,"spec_ids":null,"brand_ids":null,"custom_attribute_items":null,"del":null},"del":null,"type_id":"52","hasChildren":true,"treeParent":null,"uuid":null}]}
     * reason : 请求成功
     * success : 1
     */

    private ResultBean result;
    private String reason;
    private String success;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public static class ResultBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 4.0
             * parent_id : 0.0
             * name : 陶瓷
             * typeTemplate : {"id":53,"name":null,"spec_ids":null,"brand_ids":null,"custom_attribute_items":null,"del":null}
             * del : null
             * type_id : 53
             * hasChildren : false
             * treeParent : null
             * uuid : null
             */

            private double id;
            private double parent_id;
            private String name;
            private TypeTemplateBean typeTemplate;
            private Object del;
            private String type_id;
            private boolean hasChildren;
            private Object treeParent;
            private Object uuid;

            public double getId() {
                return id;
            }

            public void setId(double id) {
                this.id = id;
            }

            public double getParent_id() {
                return parent_id;
            }

            public void setParent_id(double parent_id) {
                this.parent_id = parent_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public TypeTemplateBean getTypeTemplate() {
                return typeTemplate;
            }

            public void setTypeTemplate(TypeTemplateBean typeTemplate) {
                this.typeTemplate = typeTemplate;
            }

            public Object getDel() {
                return del;
            }

            public void setDel(Object del) {
                this.del = del;
            }

            public String getType_id() {
                return type_id;
            }

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }

            public boolean isHasChildren() {
                return hasChildren;
            }

            public void setHasChildren(boolean hasChildren) {
                this.hasChildren = hasChildren;
            }

            public Object getTreeParent() {
                return treeParent;
            }

            public void setTreeParent(Object treeParent) {
                this.treeParent = treeParent;
            }

            public Object getUuid() {
                return uuid;
            }

            public void setUuid(Object uuid) {
                this.uuid = uuid;
            }

            public static class TypeTemplateBean {
                /**
                 * id : 53.0
                 * name : null
                 * spec_ids : null
                 * brand_ids : null
                 * custom_attribute_items : null
                 * del : null
                 */

                private double id;
                private Object name;
                private Object spec_ids;
                private Object brand_ids;
                private Object custom_attribute_items;
                private Object del;

                public double getId() {
                    return id;
                }

                public void setId(double id) {
                    this.id = id;
                }

                public Object getName() {
                    return name;
                }

                public void setName(Object name) {
                    this.name = name;
                }

                public Object getSpec_ids() {
                    return spec_ids;
                }

                public void setSpec_ids(Object spec_ids) {
                    this.spec_ids = spec_ids;
                }

                public Object getBrand_ids() {
                    return brand_ids;
                }

                public void setBrand_ids(Object brand_ids) {
                    this.brand_ids = brand_ids;
                }

                public Object getCustom_attribute_items() {
                    return custom_attribute_items;
                }

                public void setCustom_attribute_items(Object custom_attribute_items) {
                    this.custom_attribute_items = custom_attribute_items;
                }

                public Object getDel() {
                    return del;
                }

                public void setDel(Object del) {
                    this.del = del;
                }
            }
        }
    }
}
